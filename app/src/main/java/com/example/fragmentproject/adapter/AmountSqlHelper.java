package com.example.fragmentproject.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class AmountSqlHelper extends SQLiteOpenHelper {
    private String table_name = "bankAccount";
    private String TAG = getClass().getSimpleName();

    public AmountSqlHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table bankAccount(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),amount INTEGER)");
        Log.d(TAG, "onCreate: 成功创建bankAccount表");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
