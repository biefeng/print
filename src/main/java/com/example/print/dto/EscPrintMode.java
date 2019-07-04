package com.example.print.dto;

import org.springframework.util.StringUtils;

public enum EscPrintMode {
    PAGE("PAGE", 0),
    STANDARD("STANDARD", 1);

    private String mode;
    private Integer value;

    EscPrintMode(String mode, Integer value) {
        this.mode = mode;
        this.value = value;
    }

    public String getMode() {
        return mode;
    }

    public Integer getValue() {
        return value;
    }

    public static int getValByMode(String mode) {
        if (StringUtils.isEmpty(mode)) {
            return -1;
        }
        for (EscPrintMode e : values()) {
            if (e.mode.equals(mode)) {
                return e.value;
            }
        }
        return -1;
    }
}
