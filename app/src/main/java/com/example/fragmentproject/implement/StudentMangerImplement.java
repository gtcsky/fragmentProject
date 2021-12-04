package com.example.fragmentproject.implement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.fragmentproject.Bean.Student;
import com.example.fragmentproject.dao.StudentManger;

public class StudentMangerImplement implements StudentManger {
    private String TAG=getClass().getSimpleName();
    private String table_name;
    private SQLiteDatabase db;

    public StudentMangerImplement(String table_name, SQLiteDatabase db) {
        this.table_name = table_name;
        this.db = db;
    }

    @Override
    public int addStudent(Student stu) {

        ContentValues values = new ContentValues();
        values.put("name",stu.getName());
        values.put("age",stu.getAge());
        values.put("gender",stu.getGender());
        values.put("school",stu.getSchool());
        values.put("class",stu.getStdClass());
        long id=db.insert(table_name,null,values);
        if(id>0){
            Log.d(TAG, "addStudent: 添加成功");
        }else {
            Log.d(TAG, "addStudent: 添加失败");
        }
        return (int)id;
    }

    @Override
    public int deleteStudent(Student stu) {
        int id=0;
        if(stu.getStdId()!=0){
            id=db.delete(table_name,"studentId=?",new String[]{stu.getStdId()+""});
        }else{
            id=db.delete(table_name,"name=?",new String[]{stu.getName()});
        }
        return id;
    }

    @Override
    public int queryStudent(Student student) {
        Cursor cursor = null;
        if (student == null || student.getName() == null || student.getName().isEmpty()) {
            cursor = db.rawQuery("select * from "+table_name+" where studentId>?", new String[]{"0"});
        } else {

//            cursor = db.rawQuery("select * from student where name=?", new String[]{student.getName()});
            cursor = db.rawQuery("select * from "+table_name+" where name=?", new String[]{student.getName()});
        }

        if (cursor.getCount() != 0) {
            Log.d(TAG, "queryStudent: " + cursor.getCount());
            cursor.moveToFirst();
            student.setStdId(cursor.getInt(cursor.getColumnIndex("studentId")));
            student.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            student.setName(cursor.getString(cursor.getColumnIndex("name")));
            student.setSchool(cursor.getString(cursor.getColumnIndex("school")));
            student.setStdClass(cursor.getString(cursor.getColumnIndex("class")));
            student.setGender(cursor.getString(cursor.getColumnIndex("gender")));

            while (cursor.moveToNext()) {
                Log.d(TAG, "queryStudent: id="+cursor.getInt(cursor.getColumnIndex("studentId"))+"\t name"+cursor.getString(cursor.getColumnIndex("name")));
            }

            if(cursor!=null)
                cursor.close();
        } else {
            Log.d(TAG, "queryStudent: empty");
        }
        return 0;
    }

    /**                 修改学生信息
     *
     * @param ori       ori中stuId不能为空
     * @param tar       tar为修改后的信息
     * @return
     */
    @Override
    public int updateStudent(Student ori, Student tar) {

        ContentValues cv=new ContentValues();
        cv.put("school",tar.getSchool());
        cv.put("class",tar.getStdClass());
        cv.put("gender",tar.getGender());
        cv.put("name",tar.getName());
        cv.put("age",tar.getAge()+"");

        int id= db.update(table_name,cv,"studentId=?",new String[]{ori.getStdId()+""});
//        db.execSQL("update "+table_name+" set school=?,class=?,gender=?,name=?,age=? where studentId=?",new String[]{tar.getSchool(),tar.getStdClass(),tar.getGender(),tar.getName(),tar.getAge()+"",ori.getStdId()+""});
//        Log.d(TAG, "updateStudent: "+id);
        return id;
    }
}
