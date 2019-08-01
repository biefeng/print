package com.example.print.template;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/*
 *@Author BieFeNg
 *@Date 2019/7/29 15:45
 *@DESC
 */
public class DefaultResponseHandler implements ResponseHandler {
    @Override
    public byte[] handle(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = is.read(bytes)) != -1) {
            baos.write(bytes, 0, len);
        }
        return baos.toByteArray();
    }
}
