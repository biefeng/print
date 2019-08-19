package com.example.print.template.parse.entity;

/*
 *@Author BieFeNg
 *@Date 2019/8/19 11:34
 *@DESC
 */
public class ElementAttr {

    private String align;
    private String lang;
    private String list;
    private int width;
    private int height;
    private int x;
    private int y;

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "ElementAttr{" +
                "align='" + align + '\'' +
                ", lang='" + lang + '\'' +
                ", list='" + list + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
