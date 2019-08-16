package com.example.print.monitor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *@Author BieFeNg
 *@Date 2019/7/29 15:18
 *@DESC
 */
public class PrinterMonitor {

    private String startTime;
    private String endTime;
    private Timer managerTimer;
    private MonitorTaskManager manager;
    private MonitorHandler handler;
    private Map<String, TimerTask> taskMap = new ConcurrentHashMap<>();

    private Map<String, FirePrinterStatus> fires = new ConcurrentHashMap<>();
    private static final String DEVICE_ID = "local_printer";
    private static final long TIMEOUT = 1500;
    private Map<String, Long> ips = new HashMap<>();
    private static final Pattern TIME_PATTERN = Pattern.compile("([0-1]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])");
    private static final String DEFAULT_REQUEST = "<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><epos-print xmlns=\"http://www.epson-pos.com/schemas/2011/03/epos-print\"></epos-print></s:Body></s:Envelope>";

    /**
     * @param startTime 开始时间，例如10:10表示每天十点开始执行
     * @param endTime   结束时间，例如22:00表示每天晚10点结束执行
     */
    public PrinterMonitor(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * 设置监控的ip及其监控周期时长
     *
     * @param ip     "255.255.255.255"
     * @param period millseconds
     */
    public void append(String ip, long period) {
        ips.put(ip, period);
    }

    /**
     * 启动监控
     */
    public void start() {

        managerTimer = new Timer();
        manager = new MonitorTaskManager(ips);
        managerTimer.scheduleAtFixedRate(manager, getManagerStartTime(startTime), 24 * 60 * 60 * 1000);
    }

    public void stop() {

    }


    private Date getManagerStartTime(String startTime) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime localTime = LocalTime.parse(startTime);
        String dateStr = localDateTime.toLocalDate().toString() + " " + localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("时间格式错误");
        }
    }

    private class MonitorTaskManager extends TimerTask {
        Timer taskTimer;
        Map<String, Long> ips;

        public MonitorTaskManager(Map<String, Long> ips) {
            this.ips = ips;
        }

        @Override
        public void run() {
            if (taskTimer != null) {
                taskTimer.cancel();
            }
            taskTimer = new Timer();
            if (null != ips && ips.size() > 0) {
                for (Map.Entry<String, Long> e : ips.entrySet()) {
                    String key = e.getKey();
                    Long value = e.getValue();
                    if (key == null || value == null) {
                        return;
                    } else {
                        monitor(key, value);
                    }
                }
            }
        }

        public void monitor(String ip, long period) {
            MonitorTask task = new MonitorTask(taskTimer, ip);
            taskMap.put(ip, task);
            taskTimer.schedule(task, 0, period);
        }

    }

    private class MonitorTask extends TimerTask {

        private Timer timer;
        String ipAddr;
        long period;

        public MonitorTask(Timer timer, String ip) {
            this.timer = timer;
            this.ipAddr = ip;
        }

        public LocalTime getTaskEndTime() {
            Matcher m = TIME_PATTERN.matcher(endTime);
            if (!m.matches()) {
                throw new RuntimeException("请输入正确的时间格式：hh:ss");
            }
            return LocalTime.parse(endTime);
        }

        @Override
        public void run() {
            try {
                if (LocalTime.now().compareTo(getTaskEndTime()) <= 0) {
                    URL url = new URL("http://" + ipAddr + "/cgi-bin/epos/service.cgi?devid=" + DEVICE_ID + "&timeout=" + TIMEOUT);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                    conn.setRequestProperty("SOAPAction", "\"\"");
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
                    writer.write(DEFAULT_REQUEST);
                    writer.close();
                    conn.connect();

                    System.out.println("*************************" + ipAddr + " start***********************");
                    InputStream content = conn.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int a = 0;
                    while ((a = content.read()) != -1) {
                        baos.write(a);
                    }
                    String x = new String(baos.toByteArray());
                    FirePrinterStatus fireStatus = fires.get("10.128.38.241");
                    if (fireStatus == null) {
                        fireStatus = new FirePrinterStatus(x);
                    }
                    System.out.println(fireStatus.fireStatusEvent());
                    if (null != handler) {
                        MonitorResult result = fireStatus.getResult();
                        result.setIp(ipAddr);
                        handler.handle(result);
                    }
                    System.out.println("**************************" + ipAddr + " end************************");
                } else {
                    cancel();
                    if (timer.purge() == taskMap.size()) {
                        System.out.println("任务监听线程关闭！");
                        timer.cancel();
                    }
                }
            } catch (Exception e) {
                if (null != handler) {
                    MonitorResult result = new MonitorResult();
                    result.setIp(ipAddr);
                    result.setMessage("检查打印机是否开启或网络是否正常。");
                    handler.handle(result);
                }
                e.printStackTrace();
            }
        }
    }

    public MonitorHandler getHandler() {
        return handler;
    }

    public void setHandler(MonitorHandler handler) {
        this.handler = handler;
    }

    public TimerTask getMonitorTask(String ip) {
        return taskMap.get(ip);
    }

    public static void main(String[] args) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream("d:/monitor.txt");
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        PrinterMonitor monitor = new PrinterMonitor("10:00:00", "22:00:30");
        monitor.setHandler(result -> {
            try {
                if ((!result.isOnline()||!result.isCoverOk()||result.getPaperstatus()!="ok")){
                    osw.write(result.getIp()+"---"+new Date().toString()+"---"+result+"\r\n");
                }
                osw.flush();
            } catch (Exception e) {
              e.printStackTrace();
            }
        });
        monitor.append("10.128.36.242", 10000);
        /*monitor.append("10.0.1.56", 10000);
        monitor.append("10.0.1.57", 10000);
        monitor.append("10.0.1.58", 10000);
        monitor.append("10.0.1.61", 10000);*/

        monitor.start();
    }
}
