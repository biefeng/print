package com.example.print.dto;

/*
 *@Author BieFeNg
 *@Date 2019/7/4 17:23
 *@DESC
 */
public class Template1PrintData extends BasicPrintData {


    private String orderNo;
    private String id;
    private String dateStr;
    private String isPackage;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getIsPackage() {
        return isPackage;
    }

    public void setIsPackage(String isPackage) {
        this.isPackage = isPackage;
    }
}
