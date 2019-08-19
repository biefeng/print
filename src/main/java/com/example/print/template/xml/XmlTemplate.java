package com.example.print.template.xml;

import cn.hutool.setting.Setting;
import org.dom4j.Namespace;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *@Author BieFeNg
 *@Date 2019/7/18 11:26
 *@DESC
 */
public class XmlTemplate {

    private DOMDocument document;
    private DOMElement ePosPrint;
    private Namespace ePosPrintNameSpace;

    private static final Pattern ESCAPE_MARKUP_PATTERN = Pattern.compile("([<>&'\"\\t\\n\\r])");

    private StringBuilder message = new StringBuilder("<epos-print xmlns=\"http://www.epson-pos.com/schemas/2011/03/epos-print\">");

    public XmlTemplate() {
        Namespace namespace = new Namespace("s", "http://schemas.xmlsoap.org/soap/envelope/");
        DOMElement root = new DOMElement("Envelope", namespace);

        document = new DOMDocument(root);
        DOMElement sBody = new DOMElement("Body", namespace);
        root.appendChild(sBody);

        ePosPrintNameSpace = new Namespace("", "http://www.epson-pos.com/schemas/2011/03/epos-print");
        ePosPrint = new DOMElement("epos-print", ePosPrintNameSpace);
        sBody.appendChild(ePosPrint);
    }

    public void generateXmlTemplate() throws IOException {
        OutputFormat format = OutputFormat.createCompactFormat();
        format.setEncoding("utf-8");
        format.setIndent(true);
        format.setIndent("    ");
        format.setNewlines(true);

        FileWriter fw = new FileWriter("E:\\workspace\\github\\print\\src\\main\\resources\\printer.xml");
        XMLWriter xmlWriter = new XMLWriter(fw, format);
        xmlWriter.setEscapeText(false);
        xmlWriter.write(document);
        xmlWriter.flush();
    }

    public void appendText(String value) {
        DOMElement text = new DOMElement("text", ePosPrintNameSpace);
        text.setAttribute("lang", "zh-han");
        text.setAttribute("font", "font_a");
        text.setAttribute("dw", "false");
        text.setAttribute("dh", "false");
//        text.setAttribute("width", "1");
//        text.setAttribute("height", "1");
        text.setAttribute("reverse", "true");
//        text.setAttribute("x", "false");
//        text.setAttribute("y", "false");
        text.setAttribute("align", "left");
//        text.setAttribute("rotate", "false");
        text.setText(value + "&#10;");
        ePosPrint.appendChild(text);
    }

    public void appendBarCode(String value) {
        DOMElement barcode = new DOMElement("barcode", ePosPrintNameSpace);
        barcode.setAttribute("type", "code39");
        barcode.setAttribute("hri", "none");
        barcode.setAttribute("font", "font_a");
        barcode.setAttribute("width", "3");
        barcode.setAttribute("height", "100");
        barcode.setAttribute("align", "left");
        /*barcode.setAttribute("rotate", "false");*/
        barcode.setText(value);
        ePosPrint.appendChild(barcode);
    }

    public void appendFeed(int unit) {
        DOMElement feed = new DOMElement("feed", ePosPrintNameSpace);
        feed.setAttribute("unit", String.valueOf(unit));
        ePosPrint.appendChild(feed);
    }

    public void appendCut() {
        DOMElement cut = new DOMElement("cut", ePosPrintNameSpace);
        cut.setAttribute("type", "feed");
        ePosPrint.appendChild(cut);
    }

    public void addTag(String tagName, String data, Map<String, Object> attr) {
        StringBuffer sb = new StringBuffer();
        if (null != attr && attr.size() > 0) {
            sb.append(" ");
            for (Map.Entry<String, Object> entry : attr.entrySet()) {
                sb.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\" ");
            }
        }
        message.append("<").append(tagName).append(sb.toString()).append(">").append(escapeMarkup(data + "\n")).append("</").append(tagName).append(">");
    }

    public String escapeMarkup(String s) {
        Matcher m = ESCAPE_MARKUP_PATTERN.matcher(s);

        while (m.find()) {
            String g = m.group();
            String r = null;
            switch (g) {
                case "<":
                    r = "&lt;";
                    break;
                case ">":
                    r = "&gt;";
                    break;
                case "&":
                    r = "&amp;";
                    break;
                case "'":
                    r = "&apos;";
                    break;
                case "\"":
                    r = "&quot;";
                    break;
                case "\t":
                    r = "&#9;";
                    break;
                case "\n":
                    r = "&#10;";
                    break;
                case "\r":
                    r = "&#13;";
                    break;
                default:
                    break;
            }
            if (null == r) {
                continue;
            }
            s = s.replace(g, r);
        }
        return s;
    }

    public StringBuilder getMessage() {
        return message.append("</epos-print>");
    }

    public static void main(String[] args) throws IOException {
        XmlTemplate generator = new XmlTemplate();
        Map<String, Object> attr = new HashMap<>();
        attr.put("x", "245");
        attr.put("y", "100");
        attr.put("width", "1");
        attr.put("height", "1");
        generator.addTag("text", "123\n\"", attr);
        System.out.println(generator.message.toString());
    }
}
