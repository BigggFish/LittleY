package com.bigggfish.littley.model;

import android.provider.BaseColumns;

/**
 * Created by android on 2016/7/26.
 */
public final class BillContract {
    public BillContract() {};

    public static abstract class BillEntry implements BaseColumns{
        public static final String TABLE_NAME = "BILL";
        public static final String COLUMN_BILL_ID = "ID";
        public static final String COLUMN_BILL_AMOUNT = "AMOUNT";
        public static final String COLUMN_BILL_TYPEID = "TYPEID";
        public static final String COLUMN_BILL_TIME = "TIME";
        public static final String COLUMN_BILL_ISSPEND = "ISSPEND";
    }

    public static abstract class BillTypeEntry implements BaseColumns{
        public static final String TABLE_NAME = "BILLTYPE";
        public static final String COLUMN_TYPE_ID = "ID";
        public static final String COLUMN_TYPE_NAME = "NAME";
        public static final String COLUMN_TYPE_IMAGEID = "IMAGEID";
    }
}
