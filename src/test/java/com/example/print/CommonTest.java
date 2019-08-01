package com.example.print;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *@Author BieFeNg
 *@Date 2019/7/29 16:40
 *@DESC
 */
public class CommonTest {

    private static final Pattern TIME_PATTERN = Pattern.compile("([0-1]?[0-9]|2[0-3]):[0-5][0-9]");
    private static final Pattern PATTERN = Pattern.compile("^.*?success\\s*?=\\s*?\"(?<success>1|true|false)\"\\s*code\\s*?=\\s*?\"\\s*?(?<code>.*?)\\s*?\"\\s*status\\s*?=\\s*?\"\\s*?(?<status>.*?)\\s*?\"\\s*battery\\s*?=\\s*?\"\\s*?(?<battery>.*?)\\s*?\".*?", Pattern.DOTALL);

    @Test
    public void name() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2019, 7, 28), LocalTime.now());
        LocalTime localTime = localDateTime.toLocalTime();
        System.out.println(localTime);
        LocalTime now = LocalTime.now();
        System.out.println(now);
        System.out.println(now.compareTo(localTime));
    }

    @Test
    public void name1() {
        Matcher matcher = TIME_PATTERN.matcher("23:60");
        System.out.println(matcher.matches());
    }

    @Test
    public void name2() {
        String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><response success=\"false\" code=\"EPTR_COVER_OPEN\" status=\"251658300\" battery=\"0\" xmlns=\"http://www.epson-pos.com/schemas/2011/03/epos-print\"></response></s:Body></s:Envelope>";
        Matcher m = PATTERN.matcher(str);

        boolean b = m.find();
        System.out.println("result: " + b);
        if (b) {
            System.out.println(m.group("status"));
        }
    }

    @Test
    public void name3() throws ParseException {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime localTime = LocalTime.parse("10:00:31");
        String s = localDateTime.toLocalDate().toString() + " " + localTime.toString();
        System.out.println(localTime.toString());
        System.out.println(s);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(s);
        System.out.println(date);
        System.out.println(format.format(new Date()));
    }
}
