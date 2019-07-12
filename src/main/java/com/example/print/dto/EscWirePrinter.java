package com.example.print.dto;

import com.example.print.asm.EscPosWriter;
import com.example.print.asm.enums.Font;

import java.io.IOException;

/*
 *@Author BieFeNg
 *@Date 2019/7/4 16:16
 *@DESC
 */
public class EscWirePrinter implements EscPrinter {
    private boolean pageMode;
    private EscPosWriter escPosWriter;

    public EscWirePrinter(String ip, Integer mode) {
        this.escPosWriter = new EscPosWriter(ip);
        if (mode == null) {
            mode = EscPrintMode.STANDARD.getValue();
        }
        if (EscPrintMode.PAGE.getValue().equals(mode)) {
            pageMode = true;
        } else if (EscPrintMode.STANDARD.getValue().equals(mode)) {
            pageMode = false;
        }
    }

    @Override
    public void init(int wl, int wh, int hl, int hh) {

    }

    @Override
    public void text(String s, Font f, int lineHeight, int w, int h, int hL, int hH, int vL, int vH) throws IOException {

    }

    @Override
    public void barCode(String content, int type, int lineHeight, int height, int width, int pos, int hL, int hH, int vL, int vH) throws IOException {

    }

    @Override
    public void end() throws IOException {
        
    }
}
