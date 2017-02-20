package com.bigggfish.littley.model;

import android.content.Context;

import com.bigggfish.littley.model.dao.BillType;

import java.util.List;

/**
 * Created by 于祥龙 on 2017/2/20.
 */
public interface IBillTypeDataSource {

    //获取收入类型列表
    List<BillType> getIncomeBillTypeList();

    //获取花销类型列表
    List<BillType> getSpendBillTypeList();


}
