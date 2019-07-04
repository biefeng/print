package com.example.print.dto;

import java.util.List;

public class TemplateProp {
    /**
     * 模板各个元素的属性
     */
    private List<ElementProp> elements;
    /**
     * 仅对于Epson页模式下生效
     */
    private Integer printWidth;
    /**
     * 仅对于Epson页模式下生效
     */
    private Integer printHeight;
    /**
     * 模板名称
     */
    private String templateName;
    private String html;

    public List<ElementProp> getElements() {
        return elements;
    }

    public void setElements(List<ElementProp> elements) {
        this.elements = elements;
    }

    public Integer getPrintWidth() {
        return printWidth;
    }

    public void setPrintWidth(Integer printWidth) {
        this.printWidth = printWidth;
    }

    public Integer getPrintHeight() {
        return printHeight;
    }

    public void setPrintHeight(Integer printHeight) {
        this.printHeight = printHeight;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
