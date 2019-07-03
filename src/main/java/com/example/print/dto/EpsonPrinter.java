package com.example.print.dto;

public class EpsonPrinter {

    /**
     * 打印机类型：针式/热敏
     * 不同类型下，命令有些许不同，排版也有差异。
     */
    private String type;

    /**
     * 打印模式：页模式/标准模式
     * 页模式下，模板定义更加自由，对于列表数据支持有限
     * 标准模式下，模板定义不够自由，但更易完成列表数据打印
     */
    private String pageMode;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPageMode() {
        return pageMode;
    }

    public void setPageMode(String pageMode) {
        this.pageMode = pageMode;
    }
}
