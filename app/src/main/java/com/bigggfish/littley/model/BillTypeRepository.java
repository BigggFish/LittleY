package com.bigggfish.littley.model;

import android.content.Context;

import com.bigggfish.littley.model.dao.BillType;
import com.bigggfish.littley.model.local.DBManager;

import java.util.List;

/**
 * Created by bigggfish on 2017/2/20.
 * 条目类型仓库
 */
public class BillTypeRepository implements IBillTypeDataSource {

    public static BillTypeRepository mInstance = null;
    private DBManager mDBManager;

    public static BillTypeRepository getInstance(Context context){
        if(mInstance == null){
            mInstance = new BillTypeRepository(context);
        }
        return mInstance;
    }

    private BillTypeRepository(Context context){
        mDBManager = new DBManager(context);
    }


    @Override
    public List<BillType> getIncomeBillTypeList() {
        return mDBManager.queryIncomeBillType();
    }

    @Override
    public List<BillType> getSpendBillTypeList() {
        return mDBManager.querySpendBillType();
    }

}
