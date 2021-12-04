package com.example.fragmentproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;

import android.os.Bundle;

public class ConstraintActivity extends AppCompatActivity {
    Placeholder placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);
        placeholder=findViewById(R.id.id_place_holder);
        placeholder.setContentId(R.id.place_holder_left);           //把原来在其它位置显示的View 强制显示到placeholder区域

    }
}