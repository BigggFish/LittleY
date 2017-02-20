package com.bigggfish.littley.model;

import com.bigggfish.littley.model.dao.BillItem;

import java.util.List;

/**
 * Created by 于祥龙 on 2017/2/20.
 * 获取账单条目接口
 */
public interface IBillDataSource {

    //添加一个条目
    //@return false 失败
    boolean insertBill(BillItem billItem);

    //获取全部条目列表
    List<BillItem> queryAllBill();

}
