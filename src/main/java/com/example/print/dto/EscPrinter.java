package com.example.print.dto;

import com.example.print.asm.enums.Font;

import java.io.IOException;

/*
 *@Author BieFeNg
 *@Date 2019/7/4 16:21
 *@DESC
 */
public interface EscPrinter {

    void init(int wl, int wh, int hl, int hh);

    void text(String s, Font f, int w, int h, int hL, int hH, int vL, int vH) throws IOException;

    void barCode(String content, int type, int height, int width, int pos, int hL, int hH, int vL, int vH) throws IOException;

    void end() throws IOException;

}
