package com.example.print.template.jmx.bean;

import com.example.print.monitor.MonitorHandler;
import com.example.print.monitor.PrinterMonitor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/*
 *@Author BieFeNg
 *@Date 2019/8/1 19:31
 *@DESC
 */
public class MonitorOperate implements MonitorOperateMBean {

    private String startTime;
    private String endTime;

    private PrinterMonitor monitor;


    private Map<String, Long> ipMappings = new ConcurrentHashMap<>();

    public MonitorOperate() throws IOException {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("monitorIps.properties");
        Properties prop = new Properties();
        prop.load(is);

        Enumeration<?> propertyNames = prop.propertyNames();
        while (propertyNames.hasMoreElements()) {

            String propName = null;
            Object o = propertyNames.nextElement();
            if (o instanceof String) {
                propName = (String) o;
            }
            if (null == propName) {
                return;
            }
            ipMappings.put(propName, (Long) prop.get(propName));
        }
    }

    @Override
    public void add(String ip, Long period) {
        if (ip == null) {
            return;
        }
        if (period == null) {
            period = 5L;
        }
        ipMappings.put(ip, period);
    }


    @Override
    public void delete(String ip) {
        if (null == ip) {
            return;
        }
        ipMappings.remove(ip);
        if (monitor == null) {
            throw new RuntimeException("需先启动监视器才可删除任务！");
        }
        TimerTask task = monitor.getMonitorTask(ip);
        task.cancel();


    }

    public void start() {

        monitor = new PrinterMonitor(startTime, endTime);
        monitor.setHandler((result) -> {
            System.out.println(result);
        });
        ipMappings.forEach((key, value) -> {
            monitor.append(key, value);
        });
        monitor.start();
    }
}
