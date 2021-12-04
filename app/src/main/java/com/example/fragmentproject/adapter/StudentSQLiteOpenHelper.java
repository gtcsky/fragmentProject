package com.example.fragmentproject.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StudentSQLiteOpenHelper extends SQLiteOpenHelper {
    private String TAG=getClass().getSimpleName();

    public StudentSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "my.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE student(studentId INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),age INTEGER,gender VARCHAR(10),school VARCHAR(20),class VARCHAR(20))");
        Log.d(TAG, "student表创建成功 ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
