package com.bigggfish.littley.model;

import android.content.Context;

import com.bigggfish.littley.model.dao.BillItem;
import com.bigggfish.littley.model.local.DBManager;

import java.util.List;

/**
 * Created by 于祥龙 on 2017/2/20.
 * 获取账单条目仓库
 */
public class BillRepository implements IBillDataSource{

    private static BillRepository mInstance = null;
    private DBManager mDbManager;
    private Context mContext;

    public static BillRepository getInstance(Context context){
        if(mInstance == null){
            mInstance = new BillRepository(context);
        }
        return mInstance;
    }

    private BillRepository(Context context){
        mDbManager = new DBManager(context);
        mContext = context;
    }

    @Override
    public boolean insertBill(BillItem billItem) {
        if(mDbManager.insertBill(billItem) < 0){
            return false;
        }
        return true;
    }

    @Override
    public List<BillItem> queryAllBill() {
        return mDbManager.queryAllBill();
    }


}
