package com.example.print.dto;

import com.example.print.asm.enums.BarCodeTypeMappings;
import com.example.print.asm.enums.Font;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;

/*
 *@Author BieFeNg
 *@Date 2019/7/4 15:45
 *@DESC
 */
@Component
public class EscPrintDelegate {


    public void print(BasicPrintData printData, TemplateProp prop) {
        try {
            EscPrinter printer = null;
            String propStr = "{\"elements\":[{\"fontHeight\":1,\"fontType\":\"FONT-1\",\"fontWidth\":1,\"horizenPosition\":120,\"type\":\"text\",\"valueName\":\"orderNo\",\"verticalPosition\":71},{\"barCodeHeight\":100,\"barCodeType\":\"CODE128\",\"barCodeValuePosition\":\"bottom\",\"barCodeWidth\":2,\"displayBarCodeValue\":false,\"horizenPosition\":103,\"type\":\"barCode\",\"valueName\":\"id\",\"verticalPosition\":122}],\"html\":\"<div type=\\\"text\\\" class=\\\"templateElement\\\" style=\\\"position: absolute; top: 71px; cursor: default; font-weight: bolder; border: 1px solid gray; text-align: center; font-size: 13px; padding: 0px 10px; left: 120px; transform: scale(2, 2);\\\">订单号：119000023232132111</div><img class=\\\"barCodeEle templateElement selectedEle\\\" type=\\\"barCode\\\" src=\\\"data:image/png;base64,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\\\" style=\\\"position: absolute; top: 122px; cursor: default; left: 103px;\\\">\",\"printHeight\":300,\"printWidth\":450,\"templateName\":\"template1\"}";
            //TemplateProp prop = JSON.parseObject(propStr, TemplateProp.class);
            prop.setPrinterType(0);
            prop.setPrintMode(0);
            Integer printerType = prop.getPrinterType();
            Integer printMode = prop.getPrintMode();
            String ip = printData.getIp();
            if (PrinterType.EPSON_THERMAL.getValue().equals(printerType)) {
                printer = new EscThermalPrinter(ip, printMode);
            } else if (PrinterType.EPSON_THERMAL.getValue().equals(printerType)) {
                printer = new EscWirePrinter(ip, printMode);
            }
            if (printer == null)
                throw new RuntimeException("打印机创建失败，检查打印机类型");

            BigDecimal printWidth = new BigDecimal(prop.getPrintWidth());
            BigDecimal printHeight = new BigDecimal(prop.getPrintHeight());
            BigDecimal tmp = printHeight.multiply(new BigDecimal(2.8));
            //仅对EPSON打印机的标准模式设置行高
            boolean setLineHeight = (PrinterType.EPSON_THERMAL.getValue().equals(printerType) || PrinterType.EPSON_WIRE.getValue().equals(printerType)) && EscPrintMode.STANDARD.getValue().equals(prop.getPrintMode());
            //初始化打印区域,用于页模式
            printer.init(55, 2, (tmp.intValue()) % 255, (tmp.intValue()) / 255);
            for (ElementProp unit : prop.getElements()) {

                String valueName = unit.getValueName();
                if (StringUtils.isEmpty(valueName)) {
                    continue;
                }

                //如果需要设置行高，则取其值，否则置为-1
                int lineHeight = setLineHeight ? unit.getLineHeight() : -1;

                String type = unit.getType();
                BigDecimal left1 = new BigDecimal(unit.getHorizenPosition());

                BigDecimal widthMultiply = left1.divide(printWidth, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(560));
                int left = widthMultiply.intValue();
                int hl = left % 255;
                int hh = left / 255;
                BigDecimal top1 = new BigDecimal(unit.getVerticalPosition());

                BigDecimal multiplyHeight = top1.divide(printHeight, 6, BigDecimal.ROUND_HALF_UP).multiply(tmp);
                int top = multiplyHeight.intValue();
                int vl = top % 255;
                int vh = top / 255;

                String value = getValue(valueName, printData);
                switch (type) {
                    case "text":
                        printer.text(value, Font.A_SPECIAL, lineHeight, unit.getFontWidth(), unit.getFontHeight(), hl, hh, vl, vh);
                        break;
                    case "barCode":
                        int barType = BarCodeTypeMappings.getValByName(unit.getBarCodeType());
                        int barHeight = unit.getBarCodeHeight();
                        int barWidth = unit.getBarCodeWidth();
                        //条码需要重新计算垂直定位
                        multiplyHeight = new BigDecimal(unit.getVerticalPosition() + barHeight / 2).divide(printHeight, 6, BigDecimal.ROUND_HALF_UP).multiply(tmp);
                        vl = multiplyHeight.intValue() % 255;
                        vh = multiplyHeight.intValue() / 255;
                        String barCodeValuePos = unit.getBarCodeValuePosition();
                        boolean displayBarCodeValue = unit.getDisplayBarCodeValue();
                        int pos = 0;
                        if (displayBarCodeValue && !StringUtils.isEmpty(barCodeValuePos)) {
                            if (barCodeValuePos.equals("bottom")) {
                                pos = 2;
                            } else if (barCodeValuePos.equals("top")) {
                                pos = 1;
                            }
                        }
                        if (barType == 73) {
                            value = "{A" + value;
                        }
                        printer.barCode(value, barType, lineHeight, barHeight, barWidth, pos, hl, hh, vl, vh);
                        break;
                    default:
                        break;
                }
            }
            printer.end();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }

    String getValue(String valueName, BasicPrintData printData) {
        if (StringUtils.isEmpty(valueName)) {
            return null;
        }
        Class<? extends BasicPrintData> clazz = printData.getClass();
        valueName = valueName.trim();
        String methodName = "get" + valueName.substring(0, 1).toUpperCase() + valueName.substring(1);
        String result = null;
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            result = (String) method.invoke(printData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
