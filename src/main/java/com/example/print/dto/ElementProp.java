package com.example.print.dto;

public class ElementProp {
    protected Integer horizenPosition;
    protected Integer verticalPosition;
    protected String valueName;
    protected String type;

    protected Integer fontWidth;
    protected Integer fontHeight;
    protected String fontType;

    protected String barCodeType;
    protected Integer barCodeWidth;
    protected Integer barCodeHeight;
    protected Boolean displayBarCodeValue;
    protected String barCodeValuePosition;
    protected Integer lineHeight;

    public Integer getHorizenPosition() {
        return horizenPosition;
    }

    public void setHorizenPosition(Integer horizenPosition) {
        this.horizenPosition = horizenPosition;
    }

    public Integer getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(Integer verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFontWidth() {
        return fontWidth;
    }

    public void setFontWidth(Integer fontWidth) {
        this.fontWidth = fontWidth;
    }

    public Integer getFontHeight() {
        return fontHeight;
    }

    public void setFontHeight(Integer fontHeight) {
        this.fontHeight = fontHeight;
    }

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    public String getBarCodeType() {
        return barCodeType;
    }

    public void setBarCodeType(String barCodeType) {
        this.barCodeType = barCodeType;
    }

    public Integer getBarCodeWidth() {
        return barCodeWidth;
    }

    public void setBarCodeWidth(Integer barCodeWidth) {
        this.barCodeWidth = barCodeWidth;
    }

    public Integer getBarCodeHeight() {
        return barCodeHeight;
    }

    public void setBarCodeHeight(Integer barCodeHeight) {
        this.barCodeHeight = barCodeHeight;
    }

    public Boolean getDisplayBarCodeValue() {
        return displayBarCodeValue;
    }

    public void setDisplayBarCodeValue(Boolean displayBarCodeValue) {
        this.displayBarCodeValue = displayBarCodeValue;
    }

    public String getBarCodeValuePosition() {
        return barCodeValuePosition;
    }

    public void setBarCodeValuePosition(String barCodeValuePosition) {
        this.barCodeValuePosition = barCodeValuePosition;
    }

    public Integer getLineHeight() {
        return lineHeight;
    }

    public void setLineHeight(Integer lineHeight) {
        this.lineHeight = lineHeight;
    }
}
