package com.example.fragmentproject.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyDBOpenHelper extends SQLiteOpenHelper {
    private String TAG=getClass().getSimpleName();
    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
        super(context, name, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE person(personId INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20))");
        Log.d(TAG, "person表创建成功 ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE person ADD phone VARCHAR(12) NULL");
    }
}
