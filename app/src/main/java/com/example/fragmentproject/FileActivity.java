package com.example.fragmentproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;

import com.example.fragmentproject.utils.MD5Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText writeContext, readFileName, writeFileName,userName,password;
    private Button readBtn, writeBtn,submitBtn;
    private TextView readContext;
    private SharedPreferences sharedPreferences;
    private Switch aSwitch;
    private String TAG=getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        bindView();
    }

    private void bindView() {
        readBtn = findViewById(R.id.btn_read);
        writeBtn = findViewById(R.id.btn_write);
        readContext = findViewById(R.id.read_context);
        writeContext = findViewById(R.id.write_context);
        readFileName = findViewById(R.id.read_name);
        writeFileName = findViewById(R.id.write_name);
        submitBtn = findViewById(R.id.btn_submit);
        userName=findViewById(R.id.user_name);
        password=findViewById(R.id.password);
        aSwitch=findViewById(R.id.pwd_switch);

        aSwitch.setOnClickListener(this);
        readBtn.setOnClickListener(this);
        writeBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        sharedPreferences=getSharedPreferences("setting",Context.MODE_PRIVATE);
        userName.setText(sharedPreferences.getString("inputName",""));
        password.setText(sharedPreferences.getString("inputPassword",""));

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_read) {
            String fileName = readFileName.getText().toString();
            if (!isEmpty(fileName)) {
                fileName = fileName.trim();
                FileInputStream fis = null;
                try {
                    fis = this.openFileInput(fileName);
                    File file = new File(fileName);
                    byte[] bytes = new byte[100];
                    StringBuilder sb=new StringBuilder();
                    int len=0;
                    while((len=fis.read(bytes))!=-1){
                            sb.append(new String(bytes,0,len));
                    }
                    readContext.setText(sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (v.getId() == R.id.btn_write) {
            String fileName = writeFileName.getText().toString();
            String fileContext = writeContext.getText().toString();
            if (!isEmpty(fileName) && !isEmpty(fileContext)) {
                fileName = fileName.trim();
                FileOutputStream fos = null;
                try {
                    fos = this.openFileOutput(fileName, Context.MODE_APPEND);
                    fos.write(fileContext.getBytes());
                    fos.flush();
                    fos.close();
                    Toast.makeText(this, "写入完成", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        } else if (v.getId() == R.id.btn_submit) {
            String name=userName.getText().toString();
            String pwd=password.getText().toString();
            if(!isEmpty(name)&&!isEmpty(pwd)){
                name=name.trim();
                pwd=pwd.trim();

                sharedPreferences=getSharedPreferences("setting",Context.MODE_PRIVATE);
                SharedPreferences.Editor edit =sharedPreferences.edit();
                edit.putString("inputName",name);
                edit.putString("inputPassword",pwd);        

                edit.commit();
                Log.d(TAG, "onClick: "+ MD5Utils.getMD5(pwd));
            }
        } else if (v.getId() == R.id.pwd_switch) {
            if(aSwitch.isChecked()){
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);                          //设置为可见密码
            }else{
                password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);      //不可见密码
            }
        }
    }
}
