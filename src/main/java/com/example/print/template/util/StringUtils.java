package com.example.print.template.util;

/*
 *@Author BieFeNg
 *@Date 2019/8/19 13:49
 *@DESC
 */
public class StringUtils {

    public static boolean isEmpty(String s) {
        if (null == s || s.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }
}
