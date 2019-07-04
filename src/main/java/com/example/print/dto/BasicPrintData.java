package com.example.print.dto;

/*
 *@Author BieFeNg
 *@Date 2019/7/4 17:19
 *@DESC  打印数据需要继承此类，且属性类型都该配置为String类型
 */
public  class BasicPrintData {

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
