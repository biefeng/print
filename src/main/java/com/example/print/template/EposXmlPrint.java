package com.example.print.template;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;

/*
 *@Author BieFeNg
 *@Date 2019/7/15 16:06
 *@DESC
 */
public class EposXmlPrint {
    public static void main(String[] args) throws IOException {

        InputStream resource = ClassLoader.getSystemClassLoader().getResourceAsStream("printer.xml");

        StringBuffer sb = new StringBuffer();

        int len=0;
        byte[] buffer = new byte[2048];
        while ((len=resource.read(buffer))!=-1){
            sb.append(new String(buffer,0,len,"GBK"));
        }
        System.out.println(sb.toString());

        HttpClient client = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("http://10.128.38.241/cgi-bin/epos/service.cgi?devid=local_printer&timeout=10000");
        post.addHeader("Content-Type", "text/xml; charset=GBK");
        post.addHeader("SOAPAction", "\"\"");
        HttpEntity entity = new StringEntity(sb.toString());
        post.setEntity(entity);

        HttpResponse response = client.execute(post);
        HttpEntity responseEntity = response.getEntity();
        
    }
}
