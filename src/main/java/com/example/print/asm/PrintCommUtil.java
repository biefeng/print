package com.example.print.asm;

import com.example.print.asm.enums.Font;
import com.example.print.asm.enums.Height;
import com.example.print.asm.enums.Width;
import org.springframework.util.StringUtils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

import javax.imageio.ImageIO;


public class PrintCommUtil {

    private static final int ENQ = 5;
    private static final int HT = 9;
    private static final int LF = 10;
    private static final int FF = 12;
    private static final int CR = 13;
    private static final int DLE = 16;
    private static final int DC4 = 20;
    private static final int CAN = 24;
    private static final int ESC = 27;
    private static final int FS = 28;
    private static final int GS = 29;
    private static final int SP = 32;

    static int PRINT_PORT = 9100;
    Socket socket = null;
    OutputStream os = null;
    OutputStreamWriter writer = null;
    OutputStream sockoutOs;
    QRCodeUtil util = null;

    // 建立打印机连接
    public PrintCommUtil(String ip, Integer port) {
        try {
            socket = new Socket();
            if (port == null || port == 0) {
                port = PRINT_PORT;
                socket.connect(new InetSocketAddress(ip, PRINT_PORT), 1500);
            } else {
                socket.connect(new InetSocketAddress(ip, port), 1500);
            }
            os = new ByteArrayOutputStream();
            sockoutOs = socket.getOutputStream();
            writer = new OutputStreamWriter(os, "GBK");
        } catch (Exception e) {

            throw new RuntimeException("********打印机连接失败，IP: " + ip + " Port: " + port + "************");

        }
        this.util = new QRCodeUtil();
    }

    public void setFont(byte type) throws IOException {
        // 清除字体放大指令
        byte[] FONT = new byte[3];
        FONT[0] = 0x1c;
        FONT[1] = 0x21;
        FONT[2] = type;
        os.write(FONT);// 字体放大
    }

    /**
     * 指令换行
     *
     * @throws IOException
     */
    public void directLine() throws IOException {
        os.write(10);
    }

    /**
     * @param str
     * @throws IOException
     */
    public void println(String str) throws IOException {
        if (!StringUtils.isEmpty(str)) {
            print(str + "\n");
        }
    }

    public void setCharacterSize(Width width, Height height) throws IOException {
        write(ESC, '!', width.code | height.code);
    }

    public void setAbsolutePosition(int nL, int nH) throws IOException {
        write(ESC, '$', nL, nH);
    }

    public void setAbsoluteVerticalPosition(int nL, int nH) throws IOException {
        write(GS, '$', nL, nH);
    }

    public void setFont(Font font) throws IOException {
        write(ESC, 'M', font.code);
    }

    /**
     * Sends a string to the printer.
     *
     * @param text string
     * @return {@link EscPosWriter}
     */
    public void text(String text) throws IOException {
        bytes(text.getBytes());
    }

    /**
     * Sends a raw byte array to the printer.
     *
     * @param bytes byte array
     * @return {@link EscPosWriter}
     */
    public void bytes(byte[] bytes) throws IOException {

        for (byte v : bytes) {
            os.write(v);
        }
    }

    /**
     * @param str
     * @throws IOException
     */
    public void printFlush(String str) throws IOException {
        println(str);
        writer.flush();
    }

    /**
     * @param str1
     * @param str2
     * @param len
     * @throws IOException
     */
    public void println(String str1, String str2, int len) throws IOException {
        writer.write(alignStr(str1, str2, len) + " \n");
        writer.flush();
    }

    /**
     * @param str
     * @throws IOException
     */
    public void print(String str) throws IOException {
        writer.write(str);
    }

    /**
     * @param str
     * @throws IOException
     */
    public void printlnStr(String str) throws IOException {
        writer.write(str + "\n");
        writer.flush();
    }

    /**
     * 打印图片
     *
     * @param data
     * @param ins
     * @throws Exception
     */
    public void printImage(String data, InputStream ins) throws Exception {
        QRCodeUtil qrCodeUtil = new QRCodeUtil();
        BufferedImage qrImage = qrCodeUtil.createImage(data, ins, true);
        imageAndLR(qrImage, 0x87, 0x00);
    }

    /**
     * 方大字体（中英文字符）
     *
     * @param width  width=1,2,3,4,5,6   放大倍数
     * @param height height=1,2,3,4,5,6   放大倍数
     * @throws IOException
     */
    public void zoomIn(int width, int height) throws IOException {
        writer.write(0x1d);
        writer.write(0x21);
        writer.write(handle(width, height));
    }

    public int handle(int width, int height) {
        final int high = 0b11110000;
        int tmp = (width << 4) & high;
        return height + tmp;
    }

    /**
     * 切换到页模式  (可自由定位打印内容位置（水平、垂直），在此模式下需结合doPrint方法完成打印)
     *
     * @throws IOException
     */
    public void pageMoDE() throws IOException {
        writer.write(0x1b);
        writer.write(0x4c);
    }

    /*切换到标准模式*/
    public void standardMode() throws IOException {
        writer.write(0x1b);
        writer.write(0x53);
    }

    public void verticalLocate(int low, int high) throws IOException {
        writer.write(0x1d);
        writer.write(0x24);
        writer.write(low);
        writer.write(high);
    }

    public void adjustLineHight(int height) throws IOException {
        writer.write(27);
        writer.write(51);
        writer.write(height);
        //writer.write(new byte[]{27, 51, 120}); //设置行间距
    }

    public void horizenAbsoluteLocate(int low, int high) throws IOException {
        writer.write(0x1b);
        writer.write(0x24);
        writer.write(low);
        writer.write(high);
    }

    public void printArea(String x, String y, String width, String height) throws IOException {

        writer.write(0x1b);
        writer.write(0x57);

        writer.write(0x00);// n1
        writer.write(0x00);// n2
        writer.write(0x00);// n3
        writer.write(0x00);// n4

        writer.write(64);  // n5
        writer.write(3);   // n6
        writer.write(120);   // n7
        writer.write(1);   // n8
    }

    /**
     * 在pageMode下  需执行此命令完成当前页打印
     *
     * @throws IOException
     */
    public void doPagePrint() throws IOException {
        writer.write(0x0c);
    }

    public void verticalLocate() throws IOException {
        writer.write(0x1d);
        writer.write(0x24);
        writer.write(0x00);
        writer.write(0x01);
    }


    /**
     * Encode and print barcode
     *
     * @param code String to be encoded in the barcode. Different barcodes have
     *             different requirements on the length of data that can be
     *             encoded.
     * @param type Specify the type of barcode 65 = UPC-A. 66 = UPC-E. 67 =
     *             JAN13(EAN). 68 = JAN8(EAN). 69 = CODE39. 70 = ITF. 71 =
     *             CODABAR. 72 = CODE93. 73 = CODE128.
     * @param h    height of the barcode in points (1 <= n <= 255) @ param w
     *             width of module (2 <= n <=6). Barcode will not print if this
     *             value is too large. @param font Set font of HRI characters 0 =
     *             font A 1 = font B
     * @param pos  set position of HRI characters 0 = not printed. 1 = Above
     *             barcode. 2 = Below barcode. 3 = Both abo ve and below barcode.
     */
    public void printBarcode(String code, int type, int h, int w, int font,
                             int pos) throws IOException {

        // need to test for errors in length of code
        // also control for input type=0-6
        // GS H = HRI position
        writer.write(0x1D);
        writer.write("H");
        writer.write(pos); // 0=no print, 1=above, 2=below, 3=above & below

        // GS f = set barcode characters
        writer.write(0x1D);
        writer.write("f");
        writer.write(font);

        // GS h = sets barcode height
        writer.write(0x1D);
        writer.write("h");
        writer.write(h);

        // GS w = sets barcode width
        writer.write(0x1D);
        writer.write("w");
        writer.write(w);// module = 1-6

        // GS k
        writer.write(0x1D); // GS
        writer.write("k"); // k
        writer.write(type);// m = barcode type 0-6
        writer.write(code.length()); // length of encoded string
        writer.write(code);// d1-dk
        writer.write(0);// print barcode

        writer.flush();
    }


    /**
     * 左对齐字符串
     *
     * @param str1
     * @param str2
     * @param lenght 左间距
     * @return
     */
    public String alignStr(String str1, String str2, int lenght) {
        int len1 = str1.length() * 2;
        StringBuffer sb = new StringBuffer();
        sb.append(str1);
        for (; len1++ < lenght; ) {
            sb.append(" ");
        }
        sb.append(str2);
        return sb.toString();
    }


    /**
     * resets all writer settings to default
     */
    public void resetToDefault() throws IOException {
        setInverse(false);
        setBold(false);
        setFontDefault();
        setUnderline(0);
        setJustification(0);
        writer.flush();
    }

    /**
     * @throws IOException
     */
    public void setFontDefault() throws IOException {
        writer.write(0x1c);
        writer.write(0x21);
        writer.write(1);
        writer.flush();
    }

    /**
     * Sets white on black printing
     */
    public void setInverse(Boolean bool) throws IOException {
        writer.write(0x1D);
        writer.write("B");
        writer.write((int) (bool ? 1 : 0));
        writer.flush();
    }

    /**
     * Sets underline and weight
     *
     * @param val 0 = no underline. 1 = single weight underline. 2 = double
     *            weight underline.
     */
    public void setUnderline(int val) throws IOException {
        writer.write(0x1B);
        writer.write("-");
        writer.write(val);
        writer.flush();
    }

    /**
     * Sets left, center, right justification
     *
     * @param val 0 = left justify. 1 = center justify. 2 = right justify.
     */
    public void setJustification(int val) throws IOException {
        writer.write(0x1B);
        writer.write("a");
        writer.write(val);
        writer.flush();
    }

    /**
     * Sets bold
     */
    public void setBold(Boolean bool) throws IOException {
        writer.write(0x1B);
        writer.write("E");
        writer.write((int) (bool ? 1 : 0));
        writer.flush();
    }

    /**
     * @throws IOException
     */
    public void setFontZoomIn() throws IOException {
        /* 横向纵向都放大一倍 */
        writer.write(0x1c);
        writer.write(0x21);
        writer.write(12);
        writer.write(0x1b);
        writer.write(0x21);
        writer.write(12);
    }

    /**
     * @throws IOException
     */
    public void setFontZoomIn1() throws IOException {
        writer.write(0x1c);
        writer.write(0x21);
        writer.write(4);
        writer.flush();
    }

    /**
     * Encode and print QR code
     *
     * @param str        String to be encoded in QR.
     * @param errCorrect The degree of error correction. (48 <= n <= 51) 48 = level L /
     *                   7% recovery capacity. 49 = level M / 15% recovery capacity. 50
     *                   = level Q / 25% recovery capacity. 51 = level H / 30% recovery
     *                   capacity.
     * @param moduleSize The size of the QR module (pixel) in dots. The QR code will
     *                   not print if it is too big. Try setting this low and
     *                   experiment in making it larger.
     */
    public void printQR(String str, int errCorrect, int moduleSize)
            throws IOException {

        // save data function 80
        alignQr(1, moduleSize);
        writer.write(0x1D);// init
        writer.write("(k");// adjust height of barcode
        writer.write(str.length() + 3); // pl
        writer.write(0); // ph
        writer.write(49); // cn
        writer.write(80); // fn
        writer.write(48); //
        writer.write(str);

        // error correction function 69
        writer.write(0x1D);
        writer.write("(k");
        writer.write(3); // pl
        writer.write(0); // ph
        writer.write(49); // cn
        writer.write(69); // fn
        writer.write(errCorrect); // 48<= n <= 51

        // size function 67
        writer.write(0x1D);
        writer.write("(k");
        writer.write(3);
        writer.write(0);
        writer.write(49);
        writer.write(67);
        writer.write(moduleSize);// 1<= n <= 16

        // print function 81
        writer.write(0x1D);
        writer.write("(k");
        writer.write(3); // pl
        writer.write(0); // ph
        writer.write(49); // cn
        writer.write(81); // fn
        writer.write(48); // m

        writer.flush();
    }

    /**
     * 二维码排版对齐方式
     *
     * @param position   0：居左(默认) 1：居中 2：居右
     * @param moduleSize 二维码version大小
     * @return
     * @throws IOException
     */
    public void alignQr(int position, int moduleSize) throws IOException {
        writer.write(0x1B);
        writer.write(97);
        if (position == 1) {
            writer.write(1);
            centerQr(moduleSize);
        } else if (position == 2) {
            writer.write(2);
            rightQr(moduleSize);
        } else {
            writer.write(0);
        }
    }

    /**
     * 居中牌排列
     *
     * @param moduleSize 二维码version大小
     * @throws IOException
     */
    public void centerQr(int moduleSize) throws IOException {
        switch (moduleSize) {
            case 1: {
                printSpace(16);
                break;
            }
            case 2: {
                printSpace(18);
                break;
            }
            case 3: {
                printSpace(20);
                break;
            }
            case 4: {
                printSpace(22);
                break;
            }
            case 5: {
                printSpace(24);
                break;
            }
            case 6: {
                printSpace(26);
                break;
            }
            default:
                break;
        }
    }

    /**
     * 二维码居右排列
     *
     * @param moduleSize 二维码version大小
     * @throws IOException
     */
    public void rightQr(int moduleSize) throws IOException {
        switch (moduleSize) {
            case 1:
                printSpace(14);
                break;
            case 2:
                printSpace(17);
                break;
            case 3:
                printSpace(20);
                break;
            case 4:
                printSpace(23);
                break;
            case 5:
                printSpace(26);
                break;
            case 6:
                printSpace(28);
                break;
            default:
                break;
        }
    }

    /**
     * 打印空白
     *
     * @param length 需要打印空白的长度
     * @throws IOException
     */
    public void printSpace(int length) throws IOException {
        for (int i = 0; i < length; i++) {
            writer.write(" ");
        }
        writer.flush();
    }

    /**
     * 初始化打印机
     *
     * @return
     * @throws IOException
     */
    public void init() throws IOException {
        writer.write(0x1B);
        writer.write(0x40);
    }

    /**
     * 打印图片并换行
     * 由nl和nh共同确定打印的水平位置 0<nl(byte)<255和0=<nh<=2的值会被转为byte，如果nl为16或10进制，超过byte范围则会进行取余
     *
     * @param image
     * @param positionNl
     * @param positionNh
     * @throws Exception
     */
    public void imageAndLR(BufferedImage image, int positionNl, int positionNh) throws Exception {
        byte[] bytes = ImagePixelUtil.imagePixelToPosByte_24(image, 33, positionNl, positionNh);
        write(new byte[]{27, 51, 15});//设置行间距
        write(bytes);
    }

    public void image(BufferedImage image, int positionNl, int positionNh) throws Exception {
        byte[] bytes = ImagePixelUtil.imagePixelToPosByte_24(image, 33, positionNl, positionNh);
        bytes = Arrays.copyOfRange(bytes, 0, bytes.length - 5);
        //write(new byte[]{27, 51, 15});//设置行间距
        write(bytes);
    }

    public void write(byte... data) throws IOException {
        os.write(data);
        os.flush();
    }

    /**
     * To control the printer performing paper feed and cut paper finally
     *
     * @throws IOException
     */
    public void feedAndCut() throws IOException {
        feed();
        cut();
        writer.flush();
    }

    /**
     * To control the printer performing paper feed
     *
     * @throws IOException
     */
    public void feed() throws IOException {
        // 下面指令为打印完成后自动走纸
        writer.write(27);
        writer.write(100);
        writer.write(4);
        writer.write(10);

        writer.flush();

    }

    /**
     * Cut paper, functionality whether work depends on printer hardware
     *
     * @throws IOException
     */
    public void cut() throws IOException {
        // cut
        writer.write(0x1D);
        writer.write("V");
        writer.write(48);
        writer.write(0);

        writer.flush();
    }

    public void doPrint() throws IOException {
        byte[] bytes = ((ByteArrayOutputStream) os).toByteArray();
        //清空  不清空的时候在连接被缓存时会出现重复打印的问题
        ((ByteArrayOutputStream) os).reset();
        sockoutOs.write(bytes);
        sockoutOs.flush();
    }

    /**
     * at the end, close writer and socketOut
     */
    public void closeConn() {
        try {
            if (writer != null) {
                writer.close();
            }
            if (sockoutOs != null) {
                sockoutOs.close();
            }
            if (null != socket || !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void write(int val1, int val2, int val3) throws IOException {
        os.write(val1);
        os.write(val2);
        os.write(val3);
    }

    private void write(int val1, int val2, int val3, int val4) throws IOException {
        os.write(val1);
        os.write(val2);
        os.write(val3);
        os.write(val4);
    }

    public static void main(String[] args) throws Exception {
        PrintCommUtil printCommUtil = new PrintCommUtil("10.0.0.78", 9100);
        printCommUtil.write(new byte[]{27, 51, 24});
        BufferedImage read = ImageIO.read(new File("d:/doc/test/hello-world.bmp"));
        printCommUtil.image(read, 0x00, 0x00);
        //printCommUtil.feedAndCut();
    }

}
