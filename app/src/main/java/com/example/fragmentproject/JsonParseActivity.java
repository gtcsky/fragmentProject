package com.example.fragmentproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import static  android.text.TextUtils.isEmpty;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fragmentproject.Bean.Const;
import com.example.fragmentproject.Bean.OtaFileInfo;
import com.example.fragmentproject.adapter.OtaFileInfoAdapter;
import com.example.fragmentproject.utils.DownloadFileUtils;
import com.example.fragmentproject.utils.JsonUtils;
import com.example.fragmentproject.utils.MD5Utils;
import com.example.fragmentproject.utils.UrlUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class JsonParseActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = getClass().getSimpleName();
    private EditText addressInput;
    private ImageView actionGo;
    private TextView mainContent;
    private StringBuilder mainSb;
    private SharedPreferences sharedPreferences;
    private ListView listView;
    private OtaFileInfoAdapter adapter;
    //    private String respondContent;
    private final int HTTP_RESPOND = 10010;

    private OtaFileInfo selectedInfo;
    int startIndex;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == HTTP_RESPOND) {
                int total = mainSb.length();
                if (startIndex == 0)
                    mainContent.setText("");
                if (total - startIndex > 1024) {
                    mainContent.append(mainSb.subSequence(startIndex, 1024 + startIndex));
                    startIndex += 1024;
                    handler.sendEmptyMessage(HTTP_RESPOND);
                } else {
                    mainContent.append(mainSb.subSequence(startIndex, total));
                    Log.d(TAG, "handleMessage: "+mainSb.toString());
                    List<OtaFileInfo> otaFileInfos= JsonUtils.parseJsonString(mainSb.toString());
                    for(OtaFileInfo otaFileInfo:otaFileInfos){
                        Log.d(TAG, "handleMessage: "+otaFileInfo);
                    }
                    adapter.setData(otaFileInfos);

                }
            }else if(Const.FILE_DOWNLOAD_OVER==msg.what){
                Log.d(TAG, "handleMessage: download over!");
                File cacheFile = null;
                try {
                    cacheFile = UrlUtil.getFileByUrl(JsonParseActivity.this,UrlUtil.parseUrl(selectedInfo));
                    if(cacheFile.exists()&&cacheFile.length()!=0){
                        Log.d(TAG, "onItemClick: MD5="+MD5Utils.getFileMD5(cacheFile)+"\t len="+cacheFile.length());
                        Log.d(TAG, "ori: MD5="+selectedInfo.getMD5());
                        if(MD5Utils.getFileMD5(cacheFile).equalsIgnoreCase(selectedInfo.getMD5())){
                            Log.d(TAG, "handleMessage: valid File");
                        }else{
                            Log.d(TAG, "handleMessage: invalid File");
                        }
                    }else{
                        Log.d(TAG, "handleMessage: file not exist!");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parse);
        init();

    }

    public void init() {
        actionGo = findViewById(R.id.icon_go);
        addressInput = findViewById(R.id.address);
        actionGo.setOnClickListener(this);
        mainContent = findViewById(R.id.main_content);
        sharedPreferences = getSharedPreferences("setting", Context.MODE_PRIVATE);
        addressInput.setText(sharedPreferences.getString("lastJsonUrl", ""));
        listView=findViewById(R.id.id_ota_info_list);
        adapter=new OtaFileInfoAdapter(this,R.layout.item_ota_file);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedInfo= (OtaFileInfo) parent.getItemAtPosition(position);
                Log.d(TAG, "onItemClick: "+selectedInfo);

                try {
                    URL url=UrlUtil.parseUrl(selectedInfo);
                    DownloadFileUtils.download(JsonParseActivity.this,url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.icon_go) {
            String netAddr=addressInput.getText().toString();
            if(isEmpty(netAddr)||isEmpty(netAddr.trim()))
                return ;
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("lastJsonUrl",netAddr.trim());
            editor.commit();
            Thread thread=new Thread(() -> {
                URL url=null;
                HttpURLConnection conn=null;
                InputStream is=null;
                StringBuilder sb=new StringBuilder();
                try {
                    url=new URL(netAddr.trim());
                    conn= (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(3000);
                    is=conn.getInputStream();
                    byte[] bytes=new byte[1024];
                    int len=0;
                    while((len=is.read(bytes))>0){
                        sb.append(new String(bytes,0,len));
                    }
                    startIndex=0;
                    mainSb=sb;
                    handler.sendEmptyMessage(HTTP_RESPOND);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(conn!=null)
                        conn.disconnect();
                    if(is!=null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}