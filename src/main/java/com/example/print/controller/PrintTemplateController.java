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
import com.example.print.dto.ElementProp;
import com.example.print.dto.EscPrintDelegate;
import com.example.print.dto.Template1PrintData;
import com.example.print.dto.TemplateProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(allowCredentials = "true")
public class PrintTemplateController {

    @Autowired
    private EscPrintDelegate delegate;

    @GetMapping("template/list")
    public List<TemplateProp> listTemplate() {
        List<TemplateProp> lists = new ArrayList<>();
        String propStr = "{\"elements\":[{\"fontHeight\":1,\"fontType\":\"FONT-1\",\"fontWidth\":1,\"horizenPosition\":120,\"type\":\"text\",\"valueName\":\"orderNo\",\"verticalPosition\":71},{\"barCodeHeight\":100,\"barCodeType\":\"CODE128\",\"barCodeValuePosition\":\"bottom\",\"barCodeWidth\":2,\"displayBarCodeValue\":false,\"horizenPosition\":103,\"type\":\"barCode\",\"valueName\":\"id\",\"verticalPosition\":122}],\"html\":\"<div type=\\\"text\\\" class=\\\"templateElement\\\" style=\\\"position: absolute; top: 71px; cursor: default; font-weight: bolder; border: 1px solid gray; text-align: center; font-size: 13px; padding: 0px 10px; left: 120px; transform: scale(2, 2);\\\">订单号：119000023232132111</div><img class=\\\"barCodeEle templateElement selectedEle\\\" type=\\\"barCode\\\" src=\\\"data:image/png;base64,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\\\" style=\\\"position: absolute; top: 122px; cursor: default; left: 103px;\\\">\",\"printHeight\":300,\"printWidth\":450,\"templateName\":\"template1\"}";
        TemplateProp prop = JSON.parseObject(propStr, TemplateProp.class);
        lists.add(prop);
        lists.add(prop);
        lists.add(prop);
        lists.add(prop);
        lists.add(prop);
        return lists;
    }

    @PostMapping("/template/add")
    public ResponseEntity generateTemplate(HttpServletRequest request, @RequestBody TemplateProp prop) throws Exception {
        /*Map<String, String> printData = new HashMap<>();
        printData.put("ip", "10.128.38.241");
        printData.put("id", "32456");
        printData.put("deskNo", "G60");
        printData.put("isPackage", "堂食 G60");
        printData.put("qty", "规格: 2.00");
        printData.put("skuName", "清蒸多宝鱼");
        printData.put("orderNo", "订单号  : 2999111100000039054");
        printData.put("orderDate", "订单时间：2019-07-01 20:29:46");
*/
        Template1PrintData printData = new Template1PrintData();
        printData.setDateStr("订单时间：2019-07-01 20:29:46");
        printData.setOrderNo("订单号  : 2999111100000039054");
        printData.setIsPackage("堂食 A06");
        printData.setId("32456");
        printData.setIp("10.128.38.241");
        printData.setTemplateId(111L);
        delegate.print(printData, prop);
        return new ResponseEntity(null, HttpStatus.OK);
    }

    public void handleAndPrintBarCode(Map unit) {

    }
}
