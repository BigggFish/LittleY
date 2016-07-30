package com.bigggfish.littley.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bigggfish.littley.dao.BillItem;
import com.bigggfish.littley.dao.BillType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/7/26.
 * @author yu
 */
public class DBManager {

    private static final int SPEND_NUM = 14;//billtype表中前14个是支出类型。
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        dbHelper = new DBHelper(context);
    }

    /**
     * 插入一条bill数据
     * @param billItem
     * @return
     */
    public long insertBill(BillItem billItem){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BillContract.BillEntry.COLUMN_BILL_AMOUNT, billItem.getAmount());
        values.put(BillContract.BillEntry.COLUMN_BILL_TIME_STAMP, billItem.getTimeStamp());
        values.put(BillContract.BillEntry.COLUMN_BILL_BILL_TIME, billItem.getBillTime());
        values.put(BillContract.BillEntry.COLUMN_BILL_ISSPEND, billItem.isSpend() ? 1 : 0);
        values.put(BillContract.BillEntry.COLUMN_BILL_TYPEID, billItem.getBillTypeId());
        values.put(BillContract.BillEntry.COLUMN_BILL_REMARK, billItem.getBillRemark());
        long newRowId = db.insert(BillContract.BillEntry.TABLE_NAME, "null", values);
        db.close();
        return newRowId;
    }

    public long insertBill(int amount, long timeStamp, int billTime, boolean isSpend, int typeId, String remark){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BillContract.BillEntry.COLUMN_BILL_AMOUNT, amount);
        values.put(BillContract.BillEntry.COLUMN_BILL_TIME_STAMP, timeStamp);
        values.put(BillContract.BillEntry.COLUMN_BILL_BILL_TIME, billTime);
        values.put(BillContract.BillEntry.COLUMN_BILL_ISSPEND, isSpend ? 1 : 0);
        values.put(BillContract.BillEntry.COLUMN_BILL_TYPEID, typeId);
        values.put(BillContract.BillEntry.COLUMN_BILL_REMARK, remark);
        long newRowId = db.insert(BillContract.BillEntry.TABLE_NAME, "null", values);
        db.close();
        return newRowId;
    }

    /**
     * 按时间倒叙查询所有数据
     * @return
     */
    public List<BillItem> queryAllBill(){
        db = dbHelper.getReadableDatabase();
        String[] projection = {
            BillContract.BillEntry._ID,
            BillContract.BillEntry.COLUMN_BILL_AMOUNT,
            BillContract.BillEntry.COLUMN_BILL_TIME_STAMP,
            BillContract.BillEntry.COLUMN_BILL_BILL_TIME,
            BillContract.BillEntry.COLUMN_BILL_ISSPEND,
            BillContract.BillEntry.COLUMN_BILL_TYPEID,
        };
        String sortOrder = BillContract.BillEntry.COLUMN_BILL_BILL_TIME + " DESC";
        Cursor cursor = db.query(
                BillContract.BillEntry.TABLE_NAME,
                projection,
                null, //The columns for the WHERE
                null, //The values for the WHERE
                null,
                null,
                sortOrder
        );
        List<BillItem> billItemList = new ArrayList<>();
        if(cursor.moveToFirst()){
            BillItem billItem1 = new BillItem();
            billItem1.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry._ID)));
            billItem1.setTimeStamp(cursor.getLong(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_TIME_STAMP)));
            billItem1.setBillTime(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_BILL_TIME)));
            billItem1.setSpend(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_ISSPEND)) == 1);
            billItem1.setBillTypeId(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_TYPEID)));
            billItem1.setAmount(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_AMOUNT)));
            billItemList.add(billItem1);
            while(cursor.moveToNext()){
                BillItem billItem = new BillItem();
                billItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry._ID)));
                billItem.setTimeStamp(cursor.getLong(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_TIME_STAMP)));
                billItem.setBillTime(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_BILL_TIME)));
                billItem.setSpend(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_ISSPEND)) == 1);
                billItem.setBillTypeId(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_TYPEID)));
                billItem.setAmount(cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillEntry.COLUMN_BILL_AMOUNT)));
                billItemList.add(billItem);
            }
        }
        cursor.close();
        db.close();
        return billItemList;
    }

    /**
     * 删除一条记录
     * @param id 记录ID
     */
    public void deleteBill(int id){
        db = dbHelper.getWritableDatabase();
        String selection = BillContract.BillEntry._ID + " = ? " ;
        String[] selectionArgs = new String[]{String.valueOf(id)};
        db.delete(BillContract.BillEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    /**
     * 修改
     * @param id
     * @param billItem
     */
    public void updateBill(int id, BillItem billItem){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BillContract.BillEntry._ID, billItem.getId());
        values.put(BillContract.BillEntry.COLUMN_BILL_AMOUNT, billItem.getAmount());
        values.put(BillContract.BillEntry.COLUMN_BILL_TIME_STAMP, billItem.getTimeStamp());
        values.put(BillContract.BillEntry.COLUMN_BILL_BILL_TIME, billItem.getBillTime());
        values.put(BillContract.BillEntry.COLUMN_BILL_ISSPEND, billItem.isSpend() ? 1 : 0);
        values.put(BillContract.BillEntry.COLUMN_BILL_TYPEID, billItem.getBillTypeId());
        String selection = BillContract.BillEntry._ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        int count = db.update(
                BillContract.BillEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    /**
     * 前15条是支出类型
     * @return
     */
    public List<BillType> querySpendBillType(){
        db = dbHelper.getReadableDatabase();
        String[] projection = new String[]{
                BillContract.BillTypeEntry._ID,
                BillContract.BillTypeEntry.COLUMN_TYPE_NAME,
                BillContract.BillTypeEntry.COLUMN_TYPE_IMAGEID
        };
        String selection = BillContract.BillTypeEntry._ID + " <= ? ";
        String[] selectionArgs  = {String.valueOf(SPEND_NUM)};
        String sortOrder = BillContract.BillTypeEntry._ID + " ASC ";
        Cursor cursor = db.query(BillContract.BillTypeEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        List<BillType> spendBillTypeList = new ArrayList<>();

        if(cursor.moveToFirst()){
            int firstId = cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillTypeEntry._ID));
            String firstTitle = cursor.getString(cursor.getColumnIndexOrThrow(BillContract.BillTypeEntry.COLUMN_TYPE_NAME));
            int firstImageId = cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillTypeEntry.COLUMN_TYPE_IMAGEID));
            BillType firstBillType = new BillType(firstId, firstTitle, firstImageId);
            spendBillTypeList.add(firstBillType);
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillTypeEntry._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(BillContract.BillTypeEntry.COLUMN_TYPE_NAME));
                int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillTypeEntry.COLUMN_TYPE_IMAGEID));
                BillType billType = new BillType(id, title, imageId);
                spendBillTypeList.add(billType);
            }
        }
        cursor.close();
        db.close();
        return spendBillTypeList;
    }

    /**
     * 15条之后的是收入类型
     * @return
     */
    public List<BillType> queryIncomeBillType(){
        db = dbHelper.getReadableDatabase();
        String[] projection = new String[]{
                BillContract.BillTypeEntry._ID,
                BillContract.BillTypeEntry.COLUMN_TYPE_NAME,
                BillContract.BillTypeEntry.COLUMN_TYPE_IMAGEID
        };
        String selection = BillContract.BillTypeEntry._ID + " > ? ";
        String[] selectionArgs = {String.valueOf(SPEND_NUM)};
        String sortOrder = BillContract.BillTypeEntry._ID + " ASC ";
        List<BillType> incomeBillTypeList = new ArrayList<>();
        Cursor cursor = db.query(BillContract.BillTypeEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        if(cursor.moveToFirst()){
            int firstId = cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillTypeEntry._ID));
            String firstTitle = cursor.getString(cursor.getColumnIndexOrThrow(BillContract.BillTypeEntry.COLUMN_TYPE_NAME));
            int firstImageId = cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillTypeEntry.COLUMN_TYPE_IMAGEID));
            BillType firstBillType = new BillType(firstId, firstTitle, firstImageId);
            incomeBillTypeList.add(firstBillType);
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillTypeEntry._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(BillContract.BillTypeEntry.COLUMN_TYPE_NAME));
                int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(BillContract.BillTypeEntry.COLUMN_TYPE_IMAGEID));
                BillType billType = new BillType(id, title, imageId);
                incomeBillTypeList.add(billType);
            }
        }

        cursor.close();
        db.close();
        return incomeBillTypeList;
    }
}
