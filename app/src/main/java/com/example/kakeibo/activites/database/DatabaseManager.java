package com.example.kakeibo.activites.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static DatabaseManager databaseManager;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public static synchronized DatabaseManager getInstance(Context context){
        if (databaseManager == null){
            databaseManager = new DatabaseManager(context);
        }
        return databaseManager;
    }

    //書き込み用のSQLite取得
    private DatabaseManager(Context context){
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    /**
     *  ここからDBに書き込み
     */

    //支出データ書き込み
    public boolean addSpending(String category, int price, int year, int month, int days){
        final ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SPENDING_CATEGORY, category);
        values.put(DatabaseHelper.SPENDING_PRICE, price);
        //values.put(DatabaseHelper.SPENDING_CAMERA, String.valueOf(camera));
        values.put(DatabaseHelper.SPENDING_YEAR, year);
        values.put(DatabaseHelper.SPENDING_MONTH, month);
        values.put(DatabaseHelper.SPENDING_DAYS, days);
        return db.insert(DatabaseHelper.SPENDING_TABLE, null, values) != -1;
    }

    //収入データ書き込み
    public boolean addIncome(String iCategory, int iPrice, int iYear, int iMonth, int iDays){
        final ContentValues iValues = new ContentValues();
        iValues.put(DatabaseHelper.INCOME_CATEGORY, iCategory);
        iValues.put(DatabaseHelper.INCOME_PRICE, iPrice);
        iValues.put(DatabaseHelper.INCOME_YEAR, iYear);
        iValues.put(DatabaseHelper.INCOME_MONTH, iMonth);
        iValues.put(DatabaseHelper.INCOME_DAYS, iDays);
        return db.insert(DatabaseHelper.INCOME_TABLE, null, iValues) != -1;
    }

    //メモデータ書き込み
    public boolean addMemo(String mData, int mYear, int mMonth, int mDays){
        final ContentValues mValues = new ContentValues();
        mValues.put(DatabaseHelper.MEMO_DATA, mData);
        mValues.put(DatabaseHelper.MEMO_YEAR, mYear);
        mValues.put(DatabaseHelper.MEMO_MONTH, mMonth);
        mValues.put(DatabaseHelper.MEMO_DAYS, mDays);
        return db.insert(DatabaseHelper.MEMO_TABLE, null, mValues) != -1;
    }

    //カメラデータ書き込み
    public boolean addCamera(String name){
        final ContentValues cValues = new ContentValues();
        cValues.put(DatabaseHelper.CAMERA_NAME, name);
//        cValues.put(DatabaseHelper.CAMERA_IMAGE, image);
//        cValues.put(DatabaseHelper.CAMERA_YEAR, cYear);
//        cValues.put(DatabaseHelper.CAMERA_MONTH, cMonth);
//        cValues.put(DatabaseHelper.CAMERA_DAYS, cDays);
        return db.insert(DatabaseHelper.CAMERA_TABLE, null, cValues) != -1;
    }

    /**
     *  ここからDBから読み込み
     */

    //支出日付毎のデータベースの読み込み用
    public Cursor retrieveByDate(String year, String month, String days) {
        String sql = "SELECT * FROM " +  DatabaseHelper.SPENDING_TABLE + " WHERE " + DatabaseHelper.SPENDING_YEAR + "=" + year + " AND " + DatabaseHelper.SPENDING_MONTH + "=" + month + " AND " + DatabaseHelper.SPENDING_DAYS + "=" + days;
        return db.rawQuery(sql, null);
    }

    //収入日付毎のデータベースの読み込み
    public Cursor retrieveByDateI(String year, String month, String days){
        String sql = " SELECT * FROM " + DatabaseHelper.INCOME_TABLE + " WHERE " + DatabaseHelper.INCOME_YEAR + "=" + year + " AND " + DatabaseHelper.INCOME_MONTH + "=" + month + " AND " + DatabaseHelper.INCOME_DAYS + "=" + days;
        return db.rawQuery(sql, null);
    }

    //メモ日付毎のデータベースの読み込み
    public Cursor retrieveByDateM(String year, String month, String days){
        String sql = " SELECT * FROM " + DatabaseHelper.MEMO_TABLE + " WHERE " + DatabaseHelper.MEMO_YEAR + "=" + year + " AND " + DatabaseHelper.MEMO_MONTH + "=" + month + " AND " + DatabaseHelper.MEMO_DAYS + "=" + days;
        return db.rawQuery(sql, null);
    }

    //メモ全体のデータ読み込み
    public Cursor retreveAllMemoData(){
        return db.rawQuery("SELECT * FROM " + DatabaseHelper.MEMO_TABLE, null);
    }

    //カメラデータ
    public Cursor retriveCamera(){
        return db.rawQuery("SELECT * FROM " + DatabaseHelper.CAMERA_TABLE, null);
    }

    /**
     *   ここから収支の月毎の合計
     */

    //支出のデータを月ごと出力
    public Cursor retrieveDataAll(String year, String month){
        String sql = "SELECT * FROM " +  DatabaseHelper.SPENDING_TABLE + " WHERE " + DatabaseHelper.SPENDING_YEAR + "=" + year + " AND " + DatabaseHelper.SPENDING_MONTH + "=" + month;
        return db.rawQuery(sql, null);
    }

    //支出の値段合計
    public Cursor sunSpendingData(String year, String month){
        String sql = " SELECT SUM( " + DatabaseHelper.SPENDING_PRICE + " ) FROM " + DatabaseHelper.SPENDING_TABLE + " WHERE " + DatabaseHelper.SPENDING_YEAR + "=" + year + " AND " + DatabaseHelper.SPENDING_MONTH + "=" + month;
        return db.rawQuery(sql, null);
    }

    //収入のデータを月ごと出力
    public Cursor retrieveDataAllI(String year, String month){
        String sql = "SELECT * FROM " +  DatabaseHelper.INCOME_TABLE + " WHERE " + DatabaseHelper.INCOME_YEAR + "=" + year + " AND " + DatabaseHelper.INCOME_MONTH + "=" + month;
        return db.rawQuery(sql, null);
    }

    //収入の値段合計
    public Cursor sunIncomeData(String year, String month){
        String sql = " SELECT SUM( " + DatabaseHelper.INCOME_PRICE + " ) FROM " + DatabaseHelper.INCOME_TABLE + " WHERE " + DatabaseHelper.INCOME_YEAR + "=" + year + " AND " + DatabaseHelper.INCOME_MONTH + "=" + month;
        return db.rawQuery(sql, null);
    }

    /**
     *  削除用コード
     */

    //支出レコード削除
    public Cursor spendingData(int id){
        String sql = " DELETE FROM " + DatabaseHelper.SPENDING_TABLE + " WHERE " + DatabaseHelper.SPENDING_ID + "=" + id;
        return db.rawQuery(sql, null);
    }

    //収入レコード削除
    public Cursor incomeData(int id){
        String sql = " DELETE FROM " + DatabaseHelper.INCOME_TABLE + " WHERE " + DatabaseHelper.INCOME_ID + "=" + id;
        return db.rawQuery(sql, null);
    }

}
