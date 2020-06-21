package com.onethreefourtwo.puqabi;

public class Payment_Model {
    String date;
    String usd1;
    String usd2;
    public Payment_Model() {}
    public Payment_Model(String date, String usd1,String usd2){
        this.date = date;
        this.usd1 = usd1;
        this.usd2 = usd2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsd2() {
        return usd2;
    }

    public void setUsd2(String usd2) {
        this.usd2 = usd2;
    }

    public String getUsd1() {
        return usd1;
    }

    public void setUsd1(String usd1) {
        this.usd1 = usd1;
    }
}
