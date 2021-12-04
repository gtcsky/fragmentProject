package com.example.fragmentproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragmentproject.fragments.FragmentOne;

public class TransferDataActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private Button sendBtn;
    private EditText editText;
    private FragmentOne fragment;
    private OnInformationUpdateListener onInformationUpdateListener;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_data);
        sendBtn = findViewById(R.id.btn_send);
        editText = findViewById(R.id.input_text);
        textView = findViewById(R.id.received_info);
        fragment = (FragmentOne) getSupportFragmentManager().findFragmentById(R.id.fragment_one);           //androidx 写法
//        fragment=getFragmentManager().findFragmentById(R.id.fragment_one);                                //旧版本android 写法
        sendBtn.setOnClickListener(this);

        //用于接收从Fragment回传的数据
        fragment.setOnFragmentUpdateListener(new FragmentOne.OnFragmentUpdateListener() {
            @Override
            public void onFragmentUpdate(String data) {
                Toast.makeText(TransferDataActivity.this, data, Toast.LENGTH_SHORT).show();
                textView.setText(data);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send) {                                                                               //发送数据到fragment
//            Log.d(TAG, "onClick: "+editText.getText());
            Bundle bundle = new Bundle();
            bundle.putString("data", editText.getText().toString().trim());
            fragment.setArguments(bundle);                                                                              //使用setArgument方法发送数据到Fragment

            if (onInformationUpdateListener != null) {
                onInformationUpdateListener.onInformationUpdate(fragment, editText.getText().toString().trim());        //用于向Fragment发送数据
            }
        }
    }

    public interface OnInformationUpdateListener {                                                                      //用于接收来自Fragment的信息
        void onInformationUpdate(Fragment fragment, String data);
    }


    public void setFragmentUpdateListener(OnInformationUpdateListener informationUpdateListener) {                      //用于接收来自Fragment的信息
        this.onInformationUpdateListener = informationUpdateListener;
    }
}