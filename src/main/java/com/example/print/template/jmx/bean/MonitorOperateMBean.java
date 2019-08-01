package com.example.print.template.jmx.bean;

/*
 *@Author BieFeNg
 *@Date 2019/8/1 19:32
 *@DESC
 */
public interface MonitorOperateMBean {

    void add(String ip, Long period);

    void delete(String ip);

}
