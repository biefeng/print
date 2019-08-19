package com.example.print.template.parse.impl;

import com.example.print.template.parse.entity.ElementProp;
import com.example.print.dto.EscPrinter;
import com.example.print.template.parse.TemplateParser;
import com.example.print.template.parse.entity.ElementAttr;
import com.example.print.template.parse.entity.TemplateProp;
import com.example.print.template.util.CollectionUtils;
import com.example.print.template.util.StringUtils;
import com.example.print.template.xml.XmlTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/*
 *@Author BieFeNg
 *@Date 2019/8/19 11:51
 *@DESC
 */
public class TM_M30Parser implements TemplateParser {

    private EscPrinter printer;
    private XmlTemplate xmlTemplate = new XmlTemplate();

    @Override
    public byte[] parse(TemplateProp prop, Map<String, String> data) {
        ElementProp[] elements = prop.getElements();
        for (ElementProp item : elements) {
            String valueName = item.getValueName();
            String type = item.getType();
            ElementAttr attr = item.getAttr();
            String value = data.get(valueName);
            boolean b = StringUtils.isNotEmpty(value) && StringUtils.isNotEmpty(type) && CollectionUtils.isNotNullOrEmpty(parseAttr(attr));
            if (b) {
                xmlTemplate.addTag(type, value, parseAttr(attr));
            }
        }
        //
        return xmlTemplate.getMessage().toString().getBytes();
    }

    public Map<String, Object> parseAttr(ElementAttr attr) {
        Map<String, Object> result = new HashMap<>();
        Method[] declaredMethods = attr.getClass().getDeclaredMethods();
        for (Method m : declaredMethods) {
            String name = m.getName();
            Object invoke = null;
            boolean get = name.startsWith("get");
            boolean is = name.startsWith("is");
            if (get || is) {
                try {
                    invoke = m.invoke(attr, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (null == invoke) {
                    continue;
                }
                if (get) {
                    result.put(name.substring(3, 4).toLowerCase() + name.substring(4), invoke);
                } else if (is) {
                    result.put(name.substring(2, 3).toLowerCase() + name.substring(3), invoke);
                }
            }

        }
        return result;
    }


    public static void main(String[] args) {
        TemplateParser parser = new TM_M30Parser();
        parser.parse(new TemplateProp(),new HashMap<>());
    }

}
