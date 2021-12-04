package com.example.fragmentproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button transferBtn, navigationBtn,radioNavigationBtn,viewPageBtn,fileBtn,sqlBtn,constraintBtn,transactionBtn,httpBtn,jsonBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
    }

    private void bindView(){
        transferBtn = findViewById(R.id.btn_transfer);
        navigationBtn = findViewById(R.id.btn_table_menu);
        radioNavigationBtn = findViewById(R.id.btn_radio_menu);
        viewPageBtn = findViewById(R.id.btn_view_page);
        fileBtn = findViewById(R.id.btn_file);
        sqlBtn =findViewById(R.id.btn_my_sql);
        constraintBtn =findViewById(R.id.btn_constraint);
        transactionBtn =findViewById(R.id.btn_transaction);
        httpBtn =findViewById(R.id.btn_http);
        jsonBtn =findViewById(R.id.btn_json);


        viewPageBtn.setOnClickListener(this);
        transferBtn.setOnClickListener(this);
        navigationBtn.setOnClickListener(this);
        radioNavigationBtn.setOnClickListener(this);
        fileBtn.setOnClickListener(this);
        sqlBtn.setOnClickListener(this);
        constraintBtn.setOnClickListener(this);
        transactionBtn.setOnClickListener(this);
        httpBtn.setOnClickListener(this);
        jsonBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_transfer) {
            Intent intent = new Intent(MainActivity.this, TransferDataActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_table_menu) {
            Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_radio_menu) {
            Intent intent = new Intent(MainActivity.this, RadioNavigationActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_view_page) {
            Intent intent = new Intent(MainActivity.this, ViewPageActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_file) {
            Intent intent = new Intent(MainActivity.this, FileActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_my_sql) {
            Intent intent = new Intent(MainActivity.this, MySQLActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_constraint) {
            Intent intent = new Intent(MainActivity.this, ConstraintActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_transaction) {
            Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_http) {
            Intent intent = new Intent(MainActivity.this, HttpActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_json) {
            Intent intent = new Intent(MainActivity.this, JsonParseActivity.class);
            startActivity(intent);
        }
    }
}