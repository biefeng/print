package com.example.print.template.xml;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 *@Author BieFeNg
 *@Date 2019/7/30 10:37
 *@DESC
 */
public class TestResponse {

    public static void main(String[] args) throws IOException, TransformerException {
        /*HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://10.128.38.241/cgi-bin/epos/service.cgi?devid=local_printer&timeout=1000");
        request.setHeader("Content-Type", "text/xml; charset=utf-8");
        request.setHeader("SOAPAction", "\"\"");
        HttpEntity requestEntity = new StringEntity("<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><epos-print xmlns=\"http://www.epson-pos.com/schemas/2011/03/epos-print\"></epos-print></s:Body></s:Envelope>");
        request.setEntity(requestEntity);
        HttpResponse response = client.execute(request);
        HttpEntity responseEntity = response.getEntity();
        System.out.println("*************************start***********************");
        responseEntity.writeTo(System.out);
        System.out.println("*************************end***********************");*/

        URL url = new URL("http://10.128.38.241/cgi-bin/epos/service.cgi?devid=local_printer&timeout=1000");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        conn.setRequestProperty("SOAPAction", "\"\"");
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
        writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><epos-print xmlns=\"http://www.epson-pos.com/schemas/2011/03/epos-print\"></epos-print></s:Body></s:Envelope>");
        writer.close();
        conn.connect();

        BufferedInputStream bis= new BufferedInputStream(conn.getInputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int a=0;
        while ((a=bis.read())!=-1){
            baos.write(a);
        }
        System.out.println(new String(baos.toByteArray()));
        /*
        StreamSource source = new StreamSource(conn.getInputStream());
        DOMResult result = new DOMResult();
        TransformerFactory f2 = TransformerFactory.newInstance();
        Transformer t = f2.newTransformer();
        t.transform(source, result);

        Document doc = (Document) result.getNode();
        Element el = (Element) doc.getElementsByTagNameNS("http://www.epson-pos.com/schemas/2011/03/epos-print", "response").item(0);

        NamedNodeMap attributes = el.getAttributes();
        System.out.println("************************Start*********************");
        for (int i = 0; i < attributes.getLength(); i++) {
            Node item = attributes.item(i);
            System.out.println(item.getNodeValue());
        }
        System.out.println("************************End*********************");*/
    }
}
