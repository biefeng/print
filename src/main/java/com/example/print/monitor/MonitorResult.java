package com.example.print.monitor;

/*
 *@Author BieFeNg
 *@Date 2019/7/30 16:31
 *@DESC
 */
public class MonitorResult {

    private String ip;
    private boolean success = false;
    private boolean coverOk = false;
    private String paperstatus = "end";
    private boolean online = false;
    private String message;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isCoverOk() {
        return coverOk;
    }

    public void setCoverOk(boolean coverOk) {
        this.coverOk = coverOk;
    }

    public String getPaperstatus() {
        return paperstatus;
    }

    public void setPaperstatus(String paperstatus) {
        this.paperstatus = paperstatus;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MonitorResult{" +
                "ip='" + ip + '\'' +
                ", success=" + success +
                ", coverOk=" + coverOk +
                ", paperstatus='" + paperstatus + '\'' +
                ", online=" + online +
                ", message='" + message + '\'' +
                '}';
    }
}
