package com.hui.tally.db;

import java.util.Date;

public class WishBean {
    int id;
    String wishName;
    float totalMoney;
    float curMoney;
    float perMoney;
    int cycleDay;
    int done;
    String date;

    public WishBean() {
    }

    public WishBean(int id, String wishName, float totalMoney, float curMoney, float perMoney, int cycleDay, int done,String date) {
        this.id = id;
        this.wishName = wishName;
        this.totalMoney = totalMoney;
        this.curMoney = curMoney;
        this.perMoney = perMoney;
        this.cycleDay = cycleDay;
        this.done=done;
        this.date=date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWishName() {
        return wishName;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public float getCurMoney() {
        return curMoney;
    }

    public void setCurMoney(float curMoney) {
        this.curMoney = curMoney;
    }

    public float getPerMoney() {
        return perMoney;
    }

    public void setPerMoney(float perMoney) {
        this.perMoney = perMoney;
    }

    public int getCycleDay() {
        return cycleDay;
    }

    public void setCycleDay(int cycleDay) {
        this.cycleDay = cycleDay;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
