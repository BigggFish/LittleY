package com.bigggfish.littley.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by android on 2016/7/26.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "littley.db";

    public static final String SQL_CREATE_BILL_ENTRIES = " CREATE TABLE "
            + BillContract.BillEntry.TABLE_NAME
            + " ( " + BillContract.BillEntry._ID + " INTEGER PRIMARY KEY, "
            //+ BillContract.BillEntry.COLUMN_BILL_ID + " INTEGER, "
            + BillContract.BillEntry.COLUMN_BILL_AMOUNT + " INTEGER, "
            + BillContract.BillEntry.COLUMN_BILL_TIME_STAMP + " INTEGER, "
            + BillContract.BillEntry.COLUMN_BILL_BILL_TIME + " INTEGER, "
            + BillContract.BillEntry.COLUMN_BILL_ISSPEND + " INTEGER, "
            + BillContract.BillEntry.COLUMN_BILL_TYPEID + " INTEGER ,"
            + BillContract.BillEntry.COLUMN_BILL_REMARK + " TEXT )";
    public static final String SQL_DROP_BILL_ENTRIES = "DROP TABLE IF EXISTS "
            + BillContract.BillEntry.TABLE_NAME;
    public static final String SQL_CREATE_BILLTYPE_ENTRIES = "create table "
            + BillContract.BillTypeEntry.TABLE_NAME + " ( "
            + BillContract.BillTypeEntry._ID + " integer primary key,"
            + BillContract.BillTypeEntry.COLUMN_TYPE_NAME + " text ,"
            + BillContract.BillTypeEntry.COLUMN_TYPE_IMAGEID + " integer )";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Called when the database is created for the first time.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_BILL_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_BILLTYPE_ENTRIES);
        insertBillType(sqLiteDatabase);
    }

    private void insertBillType(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL("insert into billtype values (null, '餐饮', 0)");
            db.execSQL("insert into billtype values (null, '交通', 1)");
            db.execSQL("insert into billtype values (null, '衣服鞋包', 2)");
            db.execSQL("insert into billtype values (null, '日用品', 3)");
            db.execSQL("insert into billtype values (null, '通讯话费', 4)");
            db.execSQL("insert into billtype values (null, '水果零食', 5)");
            db.execSQL("insert into billtype values (null, '酒水饮料', 6)");
            db.execSQL("insert into billtype values (null, '房租', 7)");
            db.execSQL("insert into billtype values (null, '人情', 8)");
            db.execSQL("insert into billtype values (null, '药品', 9)");
            db.execSQL("insert into billtype values (null, '娱乐', 10)");
            db.execSQL("insert into billtype values (null, '家居', 11)");
            db.execSQL("insert into billtype values (null, '数码', 12)");
            db.execSQL("insert into billtype values (null, '其他', 13)");

            db.execSQL("insert into billtype values (null, '工资', 14)");
            db.execSQL("insert into billtype values (null, '报销', 15)");
            db.execSQL("insert into billtype values (null, '红包', 16)");
            db.execSQL("insert into billtype values (null, '兼职', 17)");
            db.execSQL("insert into billtype values (null, '奖金', 18)");
            db.execSQL("insert into billtype values (null, '投资', 19)");
            db.execSQL("insert into billtype values (null, '其他', 20)");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
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
