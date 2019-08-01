package com.example.print.template;

import java.io.IOException;
import java.io.InputStream;

/*
 *@Author BieFeNg
 *@Date 2019/7/29 15:43
 *@DESC
 */
public interface ResponseHandler {

    byte[] handle(InputStream is) throws IOException;

}
