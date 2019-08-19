package com.example.print;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *@Author BieFeNg
 *@Date 2019/7/29 16:40
 *@DESC
 */
public class CommonTest {

    private static final Pattern TIME_PATTERN = Pattern.compile("([0-1]?[0-9]|2[0-3]):[0-5][0-9]");
    private static final Pattern PATTERN = Pattern.compile("^.*?success\\s*?=\\s*?\"(?<success>1|true|false)\"\\s*code\\s*?=\\s*?\"\\s*?(?<code>.*?)\\s*?\"\\s*status\\s*?=\\s*?\"\\s*?(?<status>.*?)\\s*?\"\\s*battery\\s*?=\\s*?\"\\s*?(?<battery>.*?)\\s*?\".*?", Pattern.DOTALL);

    @Test
    public void name() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2019, 7, 28), LocalTime.now());
        LocalTime localTime = localDateTime.toLocalTime();
        System.out.println(localTime);
        LocalTime now = LocalTime.now();
        System.out.println(now);
        System.out.println(now.compareTo(localTime));
    }

    @Test
    public void name1() {
        Matcher matcher = TIME_PATTERN.matcher("23:60");
        System.out.println(matcher.matches());
    }

    @Test
    public void name2() {
        String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><response success=\"false\" code=\"EPTR_COVER_OPEN\" status=\"251658300\" battery=\"0\" xmlns=\"http://www.epson-pos.com/schemas/2011/03/epos-print\"></response></s:Body></s:Envelope>";
        Matcher m = PATTERN.matcher(str);

        boolean b = m.find();
        System.out.println("result: " + b);
        if (b) {
            System.out.println(m.group("status"));
        }
    }

    @Test
    public void name3() throws ParseException {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime localTime = LocalTime.parse("10:00:31");
        String s = localDateTime.toLocalDate().toString() + " " + localTime.toString();
        System.out.println(localTime.toString());
        System.out.println(s);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(s);
        System.out.println(date);
        System.out.println(format.format(new Date()));
    }


    @Test
    public void name4() throws IOException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        String ipAddr = "10.128.38.241";
        URL url = new URL("http://" + ipAddr + "/cgi-bin/epos/service.cgi?devid=local_printer" + "&timeout=" + 2500);
        service.execute(() -> {
            try {
                HttpClient client = HttpClientBuilder.create().build();
                HttpPost request = new HttpPost("http://10.128.38.241/cgi-bin/epos/service.cgi?devid=local_printer&timeout=1000");
                request.setHeader("Content-Type", "text/xml; charset=utf-8");
                request.setHeader("SOAPAction", "\"\"");
                HttpEntity requestEntity = new StringEntity("<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><epos-print xmlns=\"http://www.epson-pos.com/schemas/2011/03/epos-print\"></epos-print></s:Body></s:Envelope>");
                request.setEntity(requestEntity);
                HttpResponse response = client.execute(request);
                HttpEntity responseEntity = response.getEntity();
                System.out.println("*************************start***********************");
                responseEntity.writeTo(System.out);
                System.out.println("*************************end***********************");


                /*HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                conn.setRequestProperty("SOAPAction", "\"\"");
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
                writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><epos-print xmlns=\"http://www.epson-pos.com/schemas/2011/03/epos-print\"><text width=\"1\" x=\"31\" y=\"3\" align=\"left\" lang=\"zh-cn\" list=\"false\" height=\"2\" >订单号：199001324213453121&#10;</text><feed/><cut/></epos-print></s:Body></s:Envelope>");
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
                System.out.println(x);*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public static void main(String[] args) throws MalformedURLException {

        ExecutorService service = Executors.newFixedThreadPool(10);
        String ipAddr = "10.128.38.241";
        URL url = new URL("http://" + ipAddr + "/cgi-bin/epos/service.cgi?devid=local_printer" + "&timeout=" + 2500);
        for (int i=0;i<10;i++){
            service.execute(() -> {
                try {
                    HttpClient client = HttpClientBuilder.create().build();
                    HttpPost request = new HttpPost("http://10.128.38.241/cgi-bin/epos/service.cgi?devid=local_printer&timeout=1000");
                    request.setHeader("Content-Type", "text/xml; charset=utf-8");
                    request.setHeader("SOAPAction", "\"\"");
                    HttpEntity requestEntity = new StringEntity("<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><epos-print xmlns=\"http://www.epson-pos.com/schemas/2011/03/epos-print\"><text width=\"1\" x=\"31\" y=\"3\" align=\"left\" lang=\"zh-cn\" list=\"false\" height=\"2\" >订单号：199001324213453121&#10;</text><feed/><cut/></epos-print></s:Body></s:Envelope>");
                    request.setEntity(requestEntity);
                    HttpResponse response = client.execute(request);
                    HttpEntity responseEntity = response.getEntity();
                    System.out.println("*************************start***********************");
                    responseEntity.writeTo(System.out);
                    System.out.println("*************************end***********************");


                /*HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                conn.setRequestProperty("SOAPAction", "\"\"");
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
                writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><epos-print xmlns=\"http://www.epson-pos.com/schemas/2011/03/epos-print\"><text width=\"1\" x=\"31\" y=\"3\" align=\"left\" lang=\"zh-cn\" list=\"false\" height=\"2\" >订单号：199001324213453121&#10;</text><feed/><cut/></epos-print></s:Body></s:Envelope>");
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
                System.out.println(x);*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
