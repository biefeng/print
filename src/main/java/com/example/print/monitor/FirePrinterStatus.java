package com.example.print.monitor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *@Author BieFeNg
 *@Date 2019/7/29 21:48
 *@DESC
 */
public class FirePrinterStatus {

    private String response;

    private MonitorResult result = new MonitorResult();

    public FirePrinterStatus(String response) {
        this.response = response;
    }

    private int drawerOpenLevel = 0;
    private static final int
            ASB_NO_RESPONSE = 0,
            ASB_DRAWER_KICK = 4,
            ASB_OFF_LINE = 8,
            ASB_COVER_OPEN = 32,
            ASB_RECEIPT_NEAR_END = 131072,
            ASB_RECEIPT_END = 524288,
            DRAWER_OPEN_LEVEL_HIGH = 1;
    ;

    private int status = 0;
    private int battery = 0;

    private static final Pattern PATTERN = Pattern.compile("^.*?success\\s*?=\\s*?\"(?<success>1|true|false)\"\\s*code\\s*?=\\s*?\"\\s*?(?<code>.*?)\\s*?\"\\s*status\\s*?=\\s*?\"\\s*?(?<status>.*?)\\s*?\"\\s*battery\\s*?=\\s*?\"\\s*?(?<battery>.*?)\\s*?\".*?", Pattern.DOTALL);

    public void matchData(String response) {
        Matcher m = PATTERN.matcher(response);

        while (m.find()) {
            System.out.println(m.group("success"));
            System.out.println(m.group("code"));
            System.out.println(m.group("status"));
            System.out.println(m.group("battery"));
        }
    }

    String fireStatusEvent() {
        StringBuffer responseMsg = new StringBuffer();
        int status = 0, battery = 0;
        Matcher m = PATTERN.matcher(response);
        if (m.find()) {
            String s = m.group("status");

            String code = m.group("code");

            String success = m.group("success");
            boolean b = success.equals("1") || success.equals("true") ? true : false;
            result.setSuccess(b);

            if (!(null == s && s.trim().equals(""))) {
                status = Integer.parseInt(s);
            }
        }

        int diff;
        if (status == 0 && status == this.ASB_NO_RESPONSE) {
            status = this.status | this.ASB_NO_RESPONSE;
        }
        diff = this.status == 0 ? ~0 : this.status ^ status;
        this.status = status;
        this.battery = battery;


        if ((diff & (ASB_NO_RESPONSE | ASB_OFF_LINE)) != 0) {
            if ((status & ASB_NO_RESPONSE) != 0) {
                onpoweroff(responseMsg);
            } else {
                if ((status & ASB_OFF_LINE) != 0) {
                    onoffline(responseMsg);
                } else {
                    ononline(responseMsg);
                }
            }
        }
        if ((diff & ASB_COVER_OPEN) != 0) {
            if ((status & ASB_NO_RESPONSE) != 0) {
            } else {
                if ((status & ASB_COVER_OPEN) != 0) {
                    oncoveropen(responseMsg);
                } else {
                    oncoverok(responseMsg);
                }
            }
        }
        if ((diff & (ASB_RECEIPT_END | ASB_RECEIPT_NEAR_END)) != 0) {
            if ((status & ASB_NO_RESPONSE) != 0) {
            } else {
                if ((status & ASB_RECEIPT_END) != 0) {
                    onpaperend(responseMsg);
                } else {
                    if ((status & ASB_RECEIPT_NEAR_END) != 0) {
                        onpapernearend(responseMsg);
                    } else {
                        onpaperok(responseMsg);
                    }
                }
            }
        }
        if ((diff & ASB_DRAWER_KICK) != 0) {
            if ((status & ASB_NO_RESPONSE) != 0) {
            } else {
                if ((status & ASB_DRAWER_KICK) != 0) {
                    if (drawerOpenLevel == DRAWER_OPEN_LEVEL_HIGH) {
                        ondraweropen(responseMsg);
                    } else {
                        ondrawerclosed(responseMsg);
                    }
                    onbatterylow(responseMsg);
                } else {
                    if (drawerOpenLevel == DRAWER_OPEN_LEVEL_HIGH) {
                        ondrawerclosed(responseMsg);
                    } else {
                        ondraweropen(responseMsg);
                    }
                    onbatteryok(responseMsg);
                }
            }
        }
        String s = responseMsg.toString();
        result.setMessage(s.substring(s.length()));
        return s;
    }

    private void onbatteryok(StringBuffer responseMsg) {
        responseMsg.append("Battery Ok&&");
    }

    private void onbatterylow(StringBuffer responseMsg) {
        responseMsg.append("battery low&&");
    }

    private void ondrawerclosed(StringBuffer responseMsg) {
        responseMsg.append("Drawer closed&&");
    }

    private void ondraweropen(StringBuffer responseMsg) {
        responseMsg.append("Drawer open&&");
    }

    private void onpaperok(StringBuffer responseMsg) {
        result.setPaperstatus("ok");
        responseMsg.append("Paper Ok&&");
    }

    private void onpapernearend(StringBuffer responseMsg) {
        result.setPaperstatus("nearEnd");
        responseMsg.append("Paper near end&&");
    }

    private void onpaperend(StringBuffer responseMsg) {
        result.setPaperstatus("end");
        responseMsg.append("Paper end&&");
    }

    private void oncoverok(StringBuffer responseMsg) {
        result.setCoverOk(true);
        responseMsg.append("Cover Ok&&");
    }

    private void oncoveropen(StringBuffer responseMsg) {
        result.setCoverOk(false);
        responseMsg.append("Cover open&&");
    }

    private void ononline(StringBuffer responseMsg) {
        result.setOnline(true);
        responseMsg.append("Online&&");
    }

    private void onoffline(StringBuffer responseMsg) {
        result.setOnline(false);
        responseMsg.append("Offline&&");
    }

    private void onpoweroff(StringBuffer responseMsg) {
        responseMsg.append("Power off&&");
    }

    public MonitorResult getResult() {
        return result;
    }

    public void setResult(MonitorResult result) {
        this.result = result;
    }

    public static void main(String[] args) {
        FirePrinterStatus f = new FirePrinterStatus("<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><response success=\"true\" code=\"\" status=\"251658262\" battery=\"0\" xmlns=\"http://www.epson-pos.com/schemas/2011/03/epos-print\"></response></s:Body></s:Envelope>");
        f.matchData("");
    }
}
