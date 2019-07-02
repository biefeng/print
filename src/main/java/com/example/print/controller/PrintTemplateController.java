package com.example.print.controller;
/*
 *@Author BieFeNg
 *@Date 2019/7/2 15:03
 *@DESC
 */

import com.alibaba.fastjson.JSON;
import com.example.print.asm.TaskPrint;
import com.example.print.asm.enums.Font;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PrintTemplateController {

    @PostMapping("/template/add")
    @CrossOrigin(allowCredentials = "true")
    public ResponseEntity generateTemplate(HttpServletRequest request, @RequestBody HashMap units) throws Exception {

        Map<String, String> printData = new HashMap<>();
        printData.put("ip", "10.128.38.168");
        printData.put("deskNo", "G60");
        printData.put("isPackage", "堂食 G60");
        printData.put("qty", "规格: 2.00");
        printData.put("skuName", "清蒸多宝鱼");
        printData.put("orderNo", "订单号  : 2999111100000039054");
        printData.put("orderDate","订单时间：2019-07-01 20:29:46");


        /*List<Map<String, String>> units = JSON.parseObject(templateStr, List.class);*/
        Class<?> clazz = Class.forName("com.example.print.asm.TaskPrint");
        Constructor<?> constructor = clazz.getConstructor(String.class);
        TaskPrint taskPrint = (TaskPrint) constructor.newInstance(printData.get("ip"));
        try {
            taskPrint.init();
            for (Map unit : (List<Map>) units.get("elements")) {
                String type = (String) unit.get("type");
                BigDecimal left1 = new BigDecimal((Integer) unit.get("left"));
                BigDecimal printWidth = new BigDecimal((Integer) units.get("printWidth"));
                BigDecimal widthMultiply = left1.divide(printWidth,6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(560));
                int left = widthMultiply.intValue();
                int hl = left % 255;
                int hh = left / 255;
                BigDecimal top1 = new BigDecimal((Integer) unit.get("top"));
                BigDecimal printHeight = new BigDecimal((Integer) units.get("printHeight"));
                BigDecimal multiplyHeight = top1.divide(printHeight,6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(printHeight.intValue()*2));
                int top = multiplyHeight.intValue();
                int vl = top % 255;
                int vh = top / 255;
                switch (type) {
                    case "text":
                        taskPrint.text(printData.get(unit.get("valueName")), Font.A_SPECIAL,  Integer.parseInt((String) unit.get("fontWidth")), Integer.parseInt((String) unit.get("fontHeight")), hl, hh, vl, vh);
                        break;
                    case "qrCode":
                        taskPrint.qrCode("12345678",50,hl,hh,vl,vh);
                        break;
                }
            }
        }finally {
            taskPrint.end();
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
