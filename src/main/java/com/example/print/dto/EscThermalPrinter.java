package com.example.print.dto;

import com.example.print.asm.EscPosWriter;
import com.example.print.asm.enums.CutA;
import com.example.print.asm.enums.Font;
import com.example.print.asm.enums.Pin;
import com.example.print.asm.enums.PulseTime;

import java.io.IOException;

public class EscThermalPrinter implements EscPrinter {

    private boolean pageMode;

    private EscPosWriter escPosWriter;

    public EscThermalPrinter(String ip, Integer mode) {
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

    public void init(int wl, int wh, int hl, int hh) {
        escPosWriter.initialize();
        if (pageMode) {
            escPosWriter
                    .setPageMode()
                    .printArea(wl, wh, hl, hh);
        }
    }

    public void text(String s, Font f, int lineHeight, int w, int h, int hL, int hH, int vL, int vH) throws IOException {
        escPosWriter
                .setFont(f)
                .zoomIn(w, h)
                .setAbsolutePosition(hL, hH)
                .adjustLineHight((1 << h) * 60);
        if (pageMode) {
            escPosWriter.setAbsoluteVerticalPosition(vL, vH);
        }
        if (lineHeight != -1) {
            escPosWriter.adjustLineHight(lineHeight);
        }
        escPosWriter.text(s);
    }

    public void barCode(String content, int type, int lineHeight, int height, int width, int pos, int hL, int hH, int vL, int vH) throws IOException {
        escPosWriter.setAbsolutePosition(hL, hH);
        if (pageMode) {
            escPosWriter.setAbsoluteVerticalPosition(vL, vH);
        }
        if (lineHeight != -1) {
            escPosWriter.adjustLineHight(lineHeight);
        }
        escPosWriter.printBarcode(content, type, height, width, 0, pos);
    }

    public void end() throws IOException {
        if (pageMode) {
            escPosWriter
                    .doPagePrint()
                    .setStandardMode();
        }
        escPosWriter
                .cut(CutA.PARTIAL)
                .sendRealTimeRequestPulse(Pin.TWO, PulseTime.FOUR)
                .close();
    }
}
