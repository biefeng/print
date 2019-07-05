package com.example.print.dto;

/*
 *@Author BieFeNg
 *@Date 2019/7/4 17:19
 *@DESC  打印数据需要继承此类，并给出templateId和打印机IP
 *     打印数据字段对于文本类型可以为字符串（String,List）,对于条码则只能为字符串（String）
 */
public class BasicPrintData {

    private String ip;
    private Long templateId;

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getIp() {
        return ip;
    }

    public Long getTemplateId() {
        return templateId;
    }


}
