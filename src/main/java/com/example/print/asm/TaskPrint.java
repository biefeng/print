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
        init();
        escPosWriter.text("1111111111111111111111111111111111111111111111111111111111111111111111111");
        text("Hello Page Mode", Font.A_ALT, 0, 0, 0, 0, 100, 0);
        //qrCode("12345678", 50, 0, 1, 0, 3);
        text("11111", Font.A_SPECIAL, 1, 1, 60, 2, 0, 1);
        end();
    }

    public void init() {
        escPosWriter
                .initialize().setJustification(Justification.RIGHT)
                .setPageMode()
                .printArea(55, 2, 55, 5);
    }

    public void end() throws IOException {
        escPosWriter
                .doPagePrint()
                .setStandardMode()
                .cut(CutA.PARTIAL)
                .sendRealTimeRequestPulse(Pin.TWO, PulseTime.FOUR)
                .close();
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
        TaskPrint taskPrint = new TaskPrint("10.128.38.168");
        taskPrint.printTask();
    }
}
