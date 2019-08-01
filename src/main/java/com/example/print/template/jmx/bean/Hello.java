package com.example.print.template.jmx.bean;

import com.example.print.template.xml.XmlTemplate;

/*
 *@Author BieFeNg
 *@Date 2019/7/29 19:47
 *@DESC
 */
public class Hello implements HelloMBean {

    private String name;
    private String age;

    @Override
    public String getName() {
        System.out.println("get name:"+name);
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAge() {
        System.out.println("get age: "+age);
        return age;
    }

    @Override
    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public void helloWorld() {
        System.out.println("Hello world");
    }

    public String test(XmlTemplate template){
        return "template";
    }

    @Override
    public void helloWorld(String str) {
        System.out.println("helloWorld:" + str);
    }
}
