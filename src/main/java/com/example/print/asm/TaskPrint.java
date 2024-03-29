package com.example.print.asm;

import com.example.print.asm.EscPosWriter;
import com.example.print.asm.QRCodeUtil;
import com.example.print.asm.enums.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

/*
 *@Author BieFeNg
 *@Date 2019/7/1 21:32
 *@DESC
 */
public class TaskPrint {
    private EscPosWriter escPosWriter;
    private QRCodeUtil qrCodeUtil;

    public TaskPrint(String ip) throws IOException {
        escPosWriter = new EscPosWriter(ip);
        qrCodeUtil = new QRCodeUtil();
    }

    public void printTask() throws Exception {
        escPosWriter.initialize()
                .setAbsolutePosition(0, 1)
                .text("1111").printAndFeedLines(5)
                .cut(CutA.PARTIAL)
                .sendRealTimeRequestPulse(Pin.TWO, PulseTime.FOUR);
    }

    public void init(int wl, int wh, int hl, int hh) {
        escPosWriter
                .initialize().setJustification(Justification.RIGHT)
                .setPageMode()
                .printArea(wl, wh, hl, hh);
    }

    public void end() throws IOException {
        escPosWriter
                .doPagePrint()
                .setStandardMode()
                .cut(CutA.PARTIAL)
                .sendRealTimeRequestPulse(Pin.TWO, PulseTime.FOUR);
    }

    public void close() throws IOException {
        escPosWriter.close();
    }

    public void text(String s, Font f, int w, int h, int hL, int hH, int vL, int vH) throws IOException {
        escPosWriter//.setCharacterSize(Width.X3, Height.X5)
                .setFont(f)
                .zoomIn(w, h)
                .setAbsolutePosition(hL, hH)
                .setAbsoluteVerticalPosition(vL, vH)
                .adjustLineHight(30)
                .text(s);
    }

    public void barCode(String content, int type, int height, int width, int pos, int hL, int hH, int vL, int vH) throws IOException {
        escPosWriter.setAbsolutePosition(hL, hH)
                .setAbsoluteVerticalPosition(vL, vH)
                .printBarcode(content, type, height, width, 0, pos);
    }

    public void qrCode(String content, int lineHeight, int hl, int hh, int vl, int vh) throws Exception {
        BufferedImage qrCode = qrCodeUtil.createImage(content, null, true);
        escPosWriter
                .adjustLineHight(lineHeight)
                .setAbsoluteVerticalPosition(0, 3)
                .image(qrCode, hl, hh);
    }

    public void image() {
    }


    public static void main(String[] args) throws Exception {
        TaskPrint taskPrint = new TaskPrint("10.128.38.241");
        taskPrint.printTask();
    }
}
