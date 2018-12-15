package com.example.asus.medicinelife;

public class MedInfo {

    private String mn;
    private String mc;
    private String mf;
    private String mp;
    private String mq;

    public MedInfo(String mn, String mc, String mf, String mp, String mq) {
        this.mn = mn;
        this.mc = mc;
        this.mf = mf;
        this.mp = mp;
        this.mq = mq;
    }

    public MedInfo() {
    }

    public String getMn() {
        return mn;
    }

    public void setMn(String mn) {
        this.mn = mn;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getMf() {
        return mf;
    }

    public void setMf(String mf) {
        this.mf = mf;
    }

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public String getMq() {
        return mq;
    }

    public void setMq(String mq) {
        this.mq = mq;
    }
}
