package com.example.print.template.parse.entity;

import java.util.Arrays;

/*
 *@Author BieFeNg
 *@Date 2019/8/19 11:25
 *@DESC
 */
public class TemplateProp {

    private String templateName;

    private ElementProp[] elements;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public ElementProp[] getElements() {
        return elements;
    }

    public void setElements(ElementProp[] elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "TemplateProp{" +
                "templateName='" + templateName + '\'' +
                ", elements=" + Arrays.toString(elements) +
                '}';
    }
}
