package com.example.fragmentproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragmentproject.Bean.Student;
import com.example.fragmentproject.adapter.MyDBOpenHelper;
import com.example.fragmentproject.adapter.StudentSQLiteOpenHelper;
import com.example.fragmentproject.implement.StudentMangerImplement;

public class MySQLActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG=getClass().getSimpleName();
    private Button addBtn,delBtn,queBtn,updateBtn;
    private Button addStdBtn,delStdBtn,queStdBtn,updateStdBtn;
    private TextView resultShow;
    private EditText addText,delText,queText,updateText;
    private EditText nameText,genderText,schoolText,classText,ageText;
    private TextView idText;
    private SQLiteDatabase db,stuDb;
    private MyDBOpenHelper myDBHelper;
    private StringBuilder sb;
    private StudentMangerImplement studentMangerImplement;
    private String name,school,stdClass,gender,idString,ageString;
    private int id,age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sql);
        bindView();
    }
    private void bindView() {
        addBtn=findViewById(R.id.btn_add);
        delBtn=findViewById(R.id.btn_delete);
        updateBtn=findViewById(R.id.btn_update);
        queBtn=findViewById(R.id.btn_search);

        addText=findViewById(R.id.add_content);
        delText=findViewById(R.id.delete_content);
        updateText=findViewById(R.id.update_content);
        queText=findViewById(R.id.search_content);

        addStdBtn=findViewById(R.id.add_student);
        delStdBtn=findViewById(R.id.del_student);
        queStdBtn=findViewById(R.id.query_student);
        updateStdBtn=findViewById(R.id.update_student);

        nameText=findViewById(R.id.name_content);
        genderText=findViewById(R.id.gender_content);
        idText=findViewById(R.id.id_content);
        schoolText=findViewById(R.id.school_content);
        classText=findViewById(R.id.class_content);
        ageText=findViewById(R.id.age_content);

        resultShow=findViewById(R.id.text_view);

        addBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        queBtn.setOnClickListener(this);

        addStdBtn.setOnClickListener(this);
        delStdBtn.setOnClickListener(this);
        queStdBtn.setOnClickListener(this);
        updateStdBtn.setOnClickListener(this);

        stuDb=new StudentSQLiteOpenHelper(this,"stu.db",null,1).getWritableDatabase();
        studentMangerImplement=new StudentMangerImplement("student",stuDb);
        myDBHelper=new MyDBOpenHelper(this,"stu.db",null,1);
        db = myDBHelper.getWritableDatabase();
    }


    private void showAllDatabaseInfo(){
        Cursor  cursor=db.rawQuery("SELECT * FROM person ",null);
        sb=new StringBuilder();
        if(cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("personId"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            sb.append("id:" + id + "\t name:" + name + System.lineSeparator());
            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex("personId"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                sb.append("id:" + id + "\t name:" + name + System.lineSeparator());
            }
            resultShow.setText(sb.toString());
        }else {
            resultShow.setText("");
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {

        name=nameText.getText().toString();
        gender=genderText.getText().toString();
        school=schoolText.getText().toString();
        stdClass=classText.getText().toString();
        ageString=ageText.getText().toString();
        int result=0;
        if (v.getId() == R.id.btn_add) {
            ContentValues values = new ContentValues();
            String input = addText.getText().toString();
            if (!TextUtils.isEmpty(input)) {
                values.put("name", input.trim());
            } else {
                values.put("name", "Default Name");
            }
            db.insert("person", null, values);
            showAllDatabaseInfo();
        } else if (v.getId() == R.id.btn_delete) {
//            db.delete("person"," personId!=?",new String[]{"1"});
            String input = delText.getText().toString();
            if (!TextUtils.isEmpty(input)) {
                result=db.delete("person", " name=?", new String[]{input.trim()});

            } else {
                result=db.delete("person", " personId>?", new String[]{"0"});
            }
            if(result!=0){
                Toast.makeText(this,result+"条数据被成功删除",Toast.LENGTH_SHORT).show();
                showAllDatabaseInfo();
            }else {
                Toast.makeText(this,"没有符合删除条件的数据.",Toast.LENGTH_SHORT).show();

            }
        } else if (v.getId() == R.id.btn_update) {
            ContentValues values = new ContentValues();
            values.put("name","updated");
            String input = updateText.getText().toString();
            if (!TextUtils.isEmpty(input)) {
                result=db.update("person",values,"name=?",new String[]{input.trim()} );
            }else{      //

            }
            if(result!=0){
                Toast.makeText(this,result+"条数据完成修改",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"没有符合条件的数据.",Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btn_search) {
            showAllDatabaseInfo();
        } else if (v.getId() == R.id.add_student) {
            if(emptyInputCheck()){
                return ;
            }
            age=Integer.parseInt(ageString.trim());
            int id=studentMangerImplement.addStudent(new Student(name, gender,school, stdClass,age));
            if(id>0){
                userToast("成功增加学生"+" "+name +" 的信息.\tID="+id);
            }
        } else if (v.getId() == R.id.query_student) {
            Student student=new Student();
            idString=idText.getText().toString();
            if(!TextUtils.isEmpty(name))
                student.setName(name.trim());
            else if(!TextUtils.isEmpty(idString)){
                student.setStdId(Integer.parseInt(idString));
            }

            studentMangerImplement.queryStudent(student);
            if(student.getStdId()!=0){
                updateStudentInfoWindows(student);
            }else{
                userToast("未找到条件匹配的学生");
            }
        } else if (v.getId() == R.id.del_student) {
            if(emptyInputCheck()){
                return ;
            }
            age=Integer.parseInt(ageString.trim());
            idString=idText.getText().toString().trim();
            if(idString==null||TextUtils.isEmpty(idString.trim())){
                userToast("修改命令时ID不能为空");
                return;
            }
            Student ori=new Student();
            id=Integer.parseInt(idString);
            if(id!=0){
                ori.setStdId(id);
            }else {
                ori.setName(name);
            }
            result=studentMangerImplement.deleteStudent(ori);
            if(result!=0){
                userToast("成功删除:"+result+" 条记录");
            }
            updateStudentInfoWindows(null);
        } else if (v.getId() == R.id.update_student) {
            if(emptyInputCheck()){
                return ;
            }
            age=Integer.parseInt(ageString.trim());
            idString=idText.getText().toString().trim();
            if(idString==null||TextUtils.isEmpty(idString.trim())){
                userToast("修改命令时ID不能为空");
                return;
            }
            Student ori=new Student();
            ori.setStdId(Integer.parseInt(idString));
            result=studentMangerImplement.updateStudent(ori,new Student(name, gender,school, stdClass,age));
            if(result!=0){
                userToast("有"+result+"条记录被成功修改");
            }else {
                userToast("没有符合修改条件的记录.");
            }
        }
    }


    private void updateStudentInfoWindows(Student student){
        if(student!=null){
            nameText.setText(student.getName());
            genderText.setText(student.getGender());
            idText.setText(student.getStdId()+"");
            schoolText.setText(student.getSchool());
            classText.setText(student.getStdClass());
            ageText.setText(student.getAge()+"");
        }else{
            nameText.setText("");
            genderText.setText("");
            idText.setText("");
            schoolText.setText("");
            classText.setText("");
            ageText.setText("");
        }
    }

    /**
     *
     * @return      0/1 :target is/not empty
     */
    private boolean emptyInputCheck(){
        if(name==null||TextUtils.isEmpty(name.trim())){
            userToast("姓名不能为空");
            return true;
        }
        if(gender==null||TextUtils.isEmpty(gender.trim())){
            userToast("性别不能为空");
            return true;
        }
        if(school==null||TextUtils.isEmpty(school.trim())){
            userToast("性别学校不能为空");
            return true;
        }
        if(stdClass==null||TextUtils.isEmpty(stdClass.trim())){
            userToast("班级学校不能为空");
            return true;
        }
        if(ageString==null||TextUtils.isEmpty(ageString.trim())){
            userToast("年龄不能为空");
            return true;
        }
        return false;
    }

    private void userToast(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        stuDb.close();
    }
}