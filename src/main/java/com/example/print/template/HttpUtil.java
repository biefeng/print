package com.example.print.template;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/*
 *@Author BieFeNg
 *@Date 2019/7/29 15:30
 *@DESC
 */
public class HttpUtil {

    private static final String POST = "POST";
    private static final String GET = "GET";

    private String ip = "10.128.38.241";
    private HttpURLConnection conn;
    private Map<String, String> headers;

    public HttpUtil() {

        try {
            URL url = new URL(ip);
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String send(String method, Object data) {

        try {
            conn.setRequestMethod(method);
//          conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setHeaders(String key, String value) {
        headers.put(key, value);
        conn.setRequestProperty(key, value);

    }
}
