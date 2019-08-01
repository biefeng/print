package com.example.print.template.jmx.bean;

import com.example.print.template.xml.XmlTemplate;

/*
 *@Author BieFeNg
 *@Date 2019/7/29 19:44
 *@DESC
 */
public interface HelloMBean {

    String getName();

    void setName(String name);

    String getAge();

    void setAge(String age);

    void helloWorld();

    void helloWorld(String str);

    public String test(XmlTemplate template);

}
