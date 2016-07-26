package com.bigggfish.littley.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by android on 2016/7/26.
 */
public class BillDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "littley.db";

    public static final String SQL_CREATE_BILL_ENTRIES = "CREATE TABLE " + BillContract.BillEntry.TABLE_NAME
            + "(" + BillContract.BillEntry._ID + "INTEGER PRIMARY KEY, "
            + BillContract.BillEntry.COLUMN_BILL_ID + "INTEGER, "
            + BillContract.BillEntry.COLUMN_BILL_AMOUNT + "INTEGER, "
            + BillContract.BillEntry.COLUMN_BILL_TIME + "INTEGER, "
            + BillContract.BillEntry.COLUMN_BILL_ISSPEND + "INTEGER, "
            + BillContract.BillEntry.COLUMN_BILL_TYPEID + "INTEGER )";
    public static final String SQL_DROP_BILL_ENTRIES = "DROP TABLE IF EXISTS "
            + BillContract.BillEntry.TABLE_NAME;
    public BillDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_BILL_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DROP_BILL_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
