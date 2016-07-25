package com.bigggfish.littley.dao;

/**
 * Created by android on 2016/7/21.
 */
public class BillItem {

    private boolean isSpend;
    private boolean isNormal;

    public boolean isSpend() {
        return isSpend;
    }

    public void setSpend(boolean spend) {
        isSpend = spend;
    }

    public boolean isNormal() {
        return isNormal;
    }

    public void setNormal(boolean normal) {
        isNormal = normal;
    }

    public BillItem(boolean isNormal, boolean isSpend){
        this.isSpend = isSpend;
        this.isNormal = isNormal;
    }
}
