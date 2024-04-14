package com.example.QLChiTieu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "money_manager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "user";
    private static final String TABLE_TRANSACTIONS= "transactions";
    private static final String KEY_ID_USER = "id_user";
    private static final String KEY_ID_TRANSACTION = "id_transaction";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_DATE ="date";
    private static final String KEY_AMOUNT ="amount";
    private static final String KEY_CATEGORY ="category";
    private static final String KEY_INCOME = "income";
    private static final String KEY_NOTE = "note";
    private static final String KEY_CURRENCY = "currency";

    //We don't actually use email here

    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "("
            + KEY_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_EMAIL + " TEXT ,"
            + KEY_USERNAME+" TEXT,"
            + KEY_PASSWORD+" TEXT,"
            + KEY_CURRENCY+" TEXT );";

    private static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE "
            + TABLE_TRANSACTIONS + "("
            + KEY_ID_TRANSACTION+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_DATE + " DATE ,"
            + KEY_INCOME+" TEXT ,"
            + KEY_CATEGORY+ " TEXT,"
            + KEY_ID_USER+" INTEGER,"
            + KEY_AMOUNT+" INTEGER,"
            + KEY_NOTE+" TEXT );";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("table", CREATE_TABLE_USER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_TRANSACTIONS + "'");
        onCreate(db);
    }

    public void addUser(String email, String username, String password, String currency) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_CURRENCY, currency);

        db.insert(TABLE_USER, null, values);
    }

    public void editUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);
        db.update(TABLE_USER, values,KEY_EMAIL +" = ?", new String[]{String.valueOf(email)});
    }

    public void editUserCurrency(int userKey, String currency) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE user SET currency = '" +currency +"' WHERE id_user = '"+userKey+"'");
    }

    public void addtransaction(String date, String income, String category, int id_user ,int amount, String note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_INCOME, income);
        values.put(KEY_CATEGORY,category);
        values.put(KEY_ID_USER, id_user);
        values.put(KEY_AMOUNT,amount);
        values.put(KEY_NOTE, note);


        db.insert(TABLE_TRANSACTIONS, null, values);
    }

    public void updatetransaction(int id, String date, String income, String category, int id_user ,int amount, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_INCOME, income);
        values.put(KEY_CATEGORY,category);
        values.put(KEY_ID_USER, id_user);
        values.put(KEY_AMOUNT,amount);
        values.put(KEY_NOTE, note);
        db.update(TABLE_TRANSACTIONS, values, KEY_ID_TRANSACTION + " = ?", new String[]{String.valueOf(id)});
    }

    Cursor cursor;
    public void updateCurrency(int id,ArrayList amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.rawQuery(String.format("SELECT * FROM transactions where id_user = %s", id),null);
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            ContentValues values = new ContentValues();
            String temp = cursor.getString(0).toString();
            values.put(KEY_AMOUNT,amount.get(cc).toString());
            db.update(TABLE_TRANSACTIONS, values, KEY_ID_TRANSACTION + " = ?", new String[]{String.valueOf(temp)});
        }

    }
}
