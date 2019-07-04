package com.example.print.asm.enums;

/*
 *@Author BieFeNg
 *@Date 2019/7/3 11:16
 *@DESC
 */
public enum BarCodeTypeMappings {

    CODE128(73, "CODE128"),
    UPC(65, "UPC"),
    EAN13(67, "EAN13"),
    EAN8(68, "EAN8"),
    CODE39(69, "CODE39");


    BarCodeTypeMappings(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private int value;
    private String name;

    public static int getValByName(String name) {
        if (name == null || name.trim().equals("")) {
            return -1;
        }
        for (BarCodeTypeMappings e : values()) {
            if (e.name().equals(name)) {
                return e.value;
            }
        }
        return -1;
    }
}
