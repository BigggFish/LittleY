package com.bigggfish.littley.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bigggfish.littley.dao.BillItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/7/26.
 */
public class DBTools {

    /**
     * 插入一条bill数据
     * @param db
     * @param billItem
     * @return
     */
    public static long insertBill(SQLiteDatabase db, BillItem billItem){
        ContentValues values = new ContentValues();
        values.put(BillContract.BillEntry._ID, billItem.getId());
        values.put(BillContract.BillEntry.COLUMN_BILL_ID, billItem.getId());
        values.put(BillContract.BillEntry.COLUMN_BILL_AMOUNT, billItem.getAmount());
        values.put(BillContract.BillEntry.COLUMN_BILL_TIME, billItem.getTimeStamp());
        values.put(BillContract.BillEntry.COLUMN_BILL_ISSPEND, billItem.isSpend() ? 1 : 0);
        values.put(BillContract.BillEntry.COLUMN_BILL_TYPEID, billItem.getBillTypeId());

        long newRowId = db.insert(BillContract.BillEntry.TABLE_NAME, "null", values);
        db.close();
        return newRowId;
    }

    public static long insertBill(SQLiteDatabase db, int id, int amount, long time, boolean isSpend, int typeId){
        ContentValues values = new ContentValues();
        values.put(BillContract.BillEntry._ID, id);
        values.put(BillContract.BillEntry.COLUMN_BILL_ID, id);
        values.put(BillContract.BillEntry.COLUMN_BILL_AMOUNT, amount);
        values.put(BillContract.BillEntry.COLUMN_BILL_TIME, time);
        values.put(BillContract.BillEntry.COLUMN_BILL_ISSPEND, isSpend ? 1 : 0);
        values.put(BillContract.BillEntry.COLUMN_BILL_TYPEID, typeId);
        long newRowId = db.insert(BillContract.BillEntry.TABLE_NAME, "null", values);
        db.close();
        return newRowId;
    }

    /**
     * 按事件倒叙查询所有数据
     * @param db
     * @return
     */
    public static List<BillItem> queryAllBill(SQLiteDatabase db){

        String[] projection = {
            BillContract.BillEntry._ID,
            BillContract.BillEntry.COLUMN_BILL_ID,
            BillContract.BillEntry.COLUMN_BILL_AMOUNT,
            BillContract.BillEntry.COLUMN_BILL_TIME,
            BillContract.BillEntry.COLUMN_BILL_ISSPEND,
            BillContract.BillEntry.COLUMN_BILL_TYPEID,
        };
        String sortOrder = BillContract.BillEntry.COLUMN_BILL_TIME + " DESC";
        Cursor cursor = db.query(
                BillContract.BillEntry.TABLE_NAME,
                projection,
                null, //The columns for the WHERE
                null, //The values for the WHERE
                null,
                null,
                sortOrder
        );
        cursor.moveToFirst();
        List<BillItem> billItemList = new ArrayList<>();
        while(cursor.moveToNext()){
            BillItem billItem = new BillItem();
            billItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry._ID)));
            billItem.setTimeStamp(cursor.getLong(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_TIME)));
            billItem.setSpend(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_ISSPEND)) == 1);
            billItem.setBillTypeId(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_TYPEID)));
            billItem.setAmount(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_AMOUNT)));
            billItemList.add(billItem);
        }
        cursor.close();
        db.close();
        return billItemList;
    }

    /**
     * 删除一条记录
     * @param db
     * @param id 记录ID
     */
    public static void deleteBill(SQLiteDatabase db, int id){
        String selection = BillContract.BillEntry.COLUMN_BILL_ID + " = ? " ;
        String[] selectionArgs = new String[]{String.valueOf(id)};
        db.delete(BillContract.BillEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    /**
     * 修改
     * @param db
     * @param id
     * @param billItem
     */
    public static void updateBill(SQLiteDatabase db, int id, BillItem billItem){
        ContentValues values = new ContentValues();
        values.put(BillContract.BillEntry._ID, billItem.getId());
        values.put(BillContract.BillEntry.COLUMN_BILL_ID, billItem.getId());
        values.put(BillContract.BillEntry.COLUMN_BILL_AMOUNT, billItem.getAmount());
        values.put(BillContract.BillEntry.COLUMN_BILL_TIME, billItem.getTimeStamp());
        values.put(BillContract.BillEntry.COLUMN_BILL_ISSPEND, billItem.isSpend() ? 1 : 0);
        values.put(BillContract.BillEntry.COLUMN_BILL_TYPEID, billItem.getBillTypeId());
        String selection = BillContract.BillEntry.COLUMN_BILL_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        int count = db.update(
                BillContract.BillEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}
