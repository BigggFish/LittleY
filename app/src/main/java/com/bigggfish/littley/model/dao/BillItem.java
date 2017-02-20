package com.bigggfish.littley.model.dao;

/**
 * Created by android on 2016/7/21.
 */
public class BillItem {

    private int id;  //id
    private long timeStamp; //时间戳
    private int amount;  //总数
    private int billTypeId; //条目类别Id
    private boolean isSpend; //是否为花费
    private String billRemark; //条目注释
    private int billTime; //条目时间

    public BillItem() {
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

    public String getBillRemark() {
        return billRemark;
    }

    public void setBillRemark(String billRemark) {
        this.billRemark = billRemark;
    }

    public int getBillTime() {
        return billTime;
    }

    public void setBillTime(int billTime) {
        this.billTime = billTime;
    }
}
