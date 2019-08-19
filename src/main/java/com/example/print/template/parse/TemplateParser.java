package com.example.print.template.parse;

import com.example.print.template.parse.entity.TemplateProp;

import java.util.Map;

/*
 *@Author BieFeNg
 *@Date 2019/8/19 11:48
 *@DESC
 */
public interface TemplateParser {
    byte[] parse(TemplateProp prop, Map<String, String> data);
}
