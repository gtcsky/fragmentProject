package com.example.fragmentproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import static android.text.TextUtils.isEmpty;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragmentproject.Bean.OtaFileInfo;
import com.example.fragmentproject.utils.JsonUtils;
import com.example.fragmentproject.utils.ToastUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.BlockingDeque;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class HttpActivity extends AppCompatActivity implements View.OnClickListener , EasyPermissions.PermissionCallbacks {
    private String TAG = getClass().getSimpleName();
    private EditText addressInput;
    private ImageView actionGo;
    private TextView mainContent;
    private StringBuilder mainSb;
    //    private String respondContent;
    private final int HTTP_RESPOND = 10010;
    int start;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == HTTP_RESPOND) {
                int total=mainSb.length();
                if(total-start>1024){
                    mainContent.append(mainSb.subSequence(start,1024+start));
                    start+=1024;
                    handler.sendEmptyMessage(HTTP_RESPOND);
                }else{
                    mainContent.append(mainSb.subSequence(start,total));
                }
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        init();

    }


    private void init() {
        actionGo = findViewById(R.id.icon_go);
        addressInput = findViewById(R.id.address);
        actionGo.setOnClickListener(this);
        mainContent = findViewById(R.id.main_content);
        SharedPreferences sharedPreferences = getSharedPreferences("setting", Context.MODE_PRIVATE);
        addressInput.setText(sharedPreferences.getString("lastUrl", ""));

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.icon_go) {
            mainContent.setText("");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    initRequiredPermission();
                    String str = addressInput.getText().toString().trim();
                    if(!str.contains("://")){
                        str="http://".concat(str);                          //自动添加http://协议头
                    }
                    if (!isEmpty(str)) {
                        ToastUtil.userToast(HttpActivity.this, str);
                        URL url = null;
                        HttpURLConnection conn = null;
                        InputStream is = null;
                        try {
                            url = new URL(str);
                            conn = (HttpURLConnection) url.openConnection();
                            conn.setConnectTimeout(2000);
                            conn.setRequestMethod("GET");
                            StringBuilder sb = new StringBuilder();
                            if (conn.getResponseCode() == 200) {
                                is = conn.getInputStream();
                                byte[] bytes = new byte[1024];
                                int len = 0;
                                while ((len = is.read(bytes)) > 0) {
                                    sb.append(new String(bytes, 0, len));
                                }
                            }
                            SharedPreferences.Editor editor = getSharedPreferences("setting", Context.MODE_PRIVATE).edit();
                            editor.putString("lastUrl", str);               //保存最近一次的网址
                            editor.commit();
                            mainSb = sb;
                            start=0;
                            handler.sendEmptyMessage(HTTP_RESPOND);
                            Log.d(TAG, "run: "+sb.toString());

                        } catch (MalformedURLException | ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (is != null) {
                                    is.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            conn.disconnect();
                        }
                    }
                }
            });
            thread.start();

        }
    }

    @AfterPermissionGranted(100)
    private void initRequiredPermission() {

        String[] permissions = new String[]{Manifest.permission.INTERNET};
        boolean hasPermissions = EasyPermissions.hasPermissions(this, permissions);
        if (!hasPermissions) {
            EasyPermissions.requestPermissions(this, getString(R.string.tip_internet), 100, permissions);
        } else {
//            Log.d(TAG, "initRequiredPermission: 权限已获取");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}