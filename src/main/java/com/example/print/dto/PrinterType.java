package com.example.print.dto;

/*
 *@Author BieFeNg
 *@Date 2019/7/4 16:32
 *@DESC
 */
public enum PrinterType {

    WIRE("EPSON针式", 1),
    THERMAL("EPSON热敏", 0);

    private String type;
    private Integer value;

    PrinterType(String type, Integer value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Integer getValue() {
        return value;
    }
}
