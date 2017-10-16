package com.bigggfish.littley.model.dao;

/**
 * Created by android on 2016/7/25.
 */
public class TimeItem {

    private int id;
    private int dayAmount;
    private long timeStamp;
    private int year;
    private int month;
    private int day;
    private int billTime;

    public TimeItem(int dayAmount, int billTime){
        this.billTime = billTime;
        this.dayAmount = dayAmount;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getBillTime() {
        return billTime;
    }

    public void setBillTime(int billTime) {
        this.billTime = billTime;
    }

    public int getDayAmount() {
        return dayAmount;
    }

    public void setDayAmount(int dayAmount) {
        this.dayAmount = dayAmount;
    }
}
