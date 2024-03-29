package com.example.print.asm.enums;

public enum Status {

    ASB_BASIC(1),
    ASB_EXTENDED(2),
    OFFLINE(4),
    BATTERY(5);

    public final int code;

    Status(int code) {
        this.code = code;
    }

}
