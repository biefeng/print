package com.example.print.controller;
/*
 *@Author BieFeNg
 *@Date 2019/7/2 15:03
 *@DESC
 */

import com.alibaba.fastjson.JSON;
import com.example.print.asm.TaskPrint;
import com.example.print.asm.enums.BarCodeTypeMappings;
import com.example.print.asm.enums.Font;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
        printData.put("id", "32456");
        printData.put("deskNo", "G60");
        printData.put("isPackage", "堂食 G60");
        printData.put("qty", "规格: 2.00");
        printData.put("skuName", "清蒸多宝鱼");
        printData.put("orderNo", "订单号  : 2999111100000039054");
        printData.put("orderDate", "订单时间：2019-07-01 20:29:46");


        /*List<Map<String, String>> units = JSON.parseObject(templateStr, List.class);*/
        Class<?> clazz = Class.forName("com.example.print.asm.TaskPrint");
        Constructor<?> constructor = clazz.getConstructor(String.class);
        TaskPrint taskPrint = (TaskPrint) constructor.newInstance(printData.get("ip"));
        try {
            BigDecimal printWidth = new BigDecimal((Integer) units.get("printWidth"));
            BigDecimal printHeight = new BigDecimal((Integer) units.get("printHeight"));
            BigDecimal tmp = printHeight.multiply(new BigDecimal(2.8));
            taskPrint.init(55,2,(tmp.intValue())%255,(tmp.intValue())/255);
            for (Map unit : (List<Map>) units.get("elements")) {
                String type = (String) unit.get("type");
                BigDecimal left1 = new BigDecimal((Integer) unit.get("left"));

                BigDecimal widthMultiply = left1.divide(printWidth, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(560));
                int left = widthMultiply.intValue();
                int hl = left % 255;
                int hh = left / 255;
                BigDecimal top1 = new BigDecimal((Integer) unit.get("top"));

                BigDecimal multiplyHeight = top1.divide(printHeight, 6, BigDecimal.ROUND_HALF_UP).multiply(tmp);
                int top = multiplyHeight.intValue();
                int vl = top % 255;
                int vh = top/255;
                switch (type) {
                    case "text":
                        taskPrint.text(printData.get(unit.get("valueName")), Font.A_SPECIAL,  Integer.parseInt((String) unit.get("fontWidth")), Integer.parseInt((String) unit.get("fontHeight")), hl, hh, vl, vh);
                        break;
                    case "barCode":
                        //taskPrint.qrCode("12345678",50,hl,hh,vl,vh);
                        int barType = BarCodeTypeMappings.getValByName((String) unit.get("barCodeType"));
                        int barHeight = (int) unit.get("barCodeHeight");
                        int barWidth = (int) unit.get("barCodeWidth");
                        int barCodeValue = (int) unit.get("barCodeWidth");
                        String barCodeValuePos = (String) unit.get("barCodeValuePosition");
                        boolean displayBarCodeValue = (boolean) unit.get("displayBarCodeValue");
                        int pos = 0;
                        if (displayBarCodeValue && !StringUtils.isEmpty(barCodeValuePos)) {
                            if (barCodeValuePos.equals("bottom")) {
                                pos = 2;
                            } else if (barCodeValuePos.equals("top")) {
                                pos = 1;
                            }
                        }
                        String content = printData.get(unit.get("valueName"));
                        if (barType == 73) {
                            content = "{A" + content;
                        }
                        taskPrint.barCode(content, barType, barHeight, barWidth, pos, hl, hh, vl, vh);
                        break;
                    default:
                        break;
                }
            }
            taskPrint.end();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            taskPrint.close();
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }

    public void handleAndPrintBarCode(Map unit) {

    }
}
