package com.bigggfish.littley.dao;

/**
 * Created by android on 2016/7/21.
 */
public class BillItem {

    private int id;
    private long timeStamp;
    private int amount;
    private int billTypeId;
    private boolean isSpend;

    public BillItem(){
    }
    public boolean isSpend() {
        return isSpend;
    }

    public void setSpend(boolean spend) {
        isSpend = spend;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBillTypeId() {
        return billTypeId;
    }

    public void setBillTypeId(int billTypeId) {
        this.billTypeId = billTypeId;
    }
}
