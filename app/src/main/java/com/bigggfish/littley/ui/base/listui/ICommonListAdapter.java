package com.bigggfish.littley.ui.base.listui;

import java.util.List;

/**
 * Created by bigggfish on 2017/10/18.
 *
 * adapter数据 基础操作
 */

public interface ICommonListAdapter<T> {

    void clearData();

    void addAllData(List<T> data);

    void replaceAllData(List<T> data);

}
