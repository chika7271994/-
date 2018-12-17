package com.example.kakeibo.activites.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // データーベースのバージョン
    private static final int DATABASE_VERSION = 1;

    // データーベース名
    private static final String DATABASE_NAME = "Kakeibo.db";

    //支出テーブル
    public static final String SPENDING_TABLE = "spending_table";
    //収入テーブル
    public static final String INCOME_TABLE = "income_table";
    //メモテーブル
    public static final String MEMO_TABLE = "memo_table";

    //支出カラム
    public static final String SPENDING_ID = "s_id";
    public static final String SPENDING_CATEGORY = "s_category";
    public static final String SPENDING_PRICE = "s_price";
    public static final String SPENDING_YEAR = "s_year";
    public static final String SPENDING_MONTH = "s_month";
    public static final String SPENDING_DAYS = "s_days";
    public static final String PIC_LOCATION = "location";

    //収入カラム
    public static final String INCOME_ID = "i_id";
    public static final String INCOME_CATEGORY = "i_category";
    public static final String INCOME_PRICE = "i_price";
    public static final String INCOME_YEAR = "i_year";
    public static final String INCOME_MONTH = "i_month";
    public static final String INCOME_DAYS = "i_days";

    //メモカラム
    public static final String MEMO_ID = "m_id";
    public static final String MEMO_DATA = "memo";
    public static final String MEMO_YEAR = "m_year";
    public static final String MEMO_MONTH = "m_month";
    public static final String MEMO_DAYS = "m_days";



    //支出テーブル作成
    private static final String SQL_CREATE_SPENDING = " CREATE TABLE IF NOT EXISTS " + SPENDING_TABLE +
            " ( " +
            SPENDING_ID       + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            SPENDING_CATEGORY + " TEXT NOT NULL, "    +
            SPENDING_PRICE    + " INTEGER NOT NULL, " +
            SPENDING_YEAR     + " INTEGER NOT NULL, " +
            SPENDING_MONTH    + " INTEGER NOT NULL, " +
            SPENDING_DAYS     + " INTEGER NOT NULL, "  +
            PIC_LOCATION      + " TEXT " +
            " ) ";

    //収入テーブル作成
    private static final String SQL_CREATE_INCOME = " CREATE TABLE IF NOT EXISTS " + INCOME_TABLE +
            " ( " +
            INCOME_ID       + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            INCOME_CATEGORY + " TEXT NOT NULL, "    +
            INCOME_PRICE    + " INTEGER NOT NULL, " +
            INCOME_YEAR     + " INTEGER NOT NULL, " +
            INCOME_MONTH    + " INTEGER NOT NULL, " +
            INCOME_DAYS     + " INTEGER NOT NULL "  +
            " ) ";

    //メモテーブル作成
    private static final String SQL_CREATE_MEMO = " CREATE TABLE IF NOT EXISTS " + MEMO_TABLE +
            " ( " +
            MEMO_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MEMO_DATA  + " TEXT, " +
            MEMO_YEAR  + " TEXT, " +
            MEMO_MONTH + " TEXT, " +
            MEMO_DAYS  + " TEXT"   +
            " ) ";


    DatabaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //テーブルクリエイト
        db.execSQL(SQL_CREATE_SPENDING);
        db.execSQL(SQL_CREATE_INCOME);
        db.execSQL(SQL_CREATE_MEMO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // アップデートの判別
        db.execSQL("DROP TABLE IF EXISTS " + SPENDING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INCOME_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MEMO_TABLE);

        //アップデートしたテーブルを作成
        onCreate(db);
    }
}
