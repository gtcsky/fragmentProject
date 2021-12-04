package com.example.fragmentproject.utils;

import android.content.Context;
import android.util.Log;

import com.example.fragmentproject.Bean.OtaFileInfo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtil {
    public static final String PRE_ADDRESS = "http://bough2.host.com263.cn/BoughOTAFiles/";

    public static URL parseUrl(OtaFileInfo otaFileInfo) throws MalformedURLException {
        if (otaFileInfo == null)
            return null;
        String model = otaFileInfo.getModel();
        String temp = model.substring(0, model.length() - 4);
        String urlString = otaFileInfo.getUrl();
        urlString = PRE_ADDRESS + temp + urlString;
        Log.d("UrlUtil", "parseUrl: " + urlString);
        URL url = new URL(urlString);
        return url;
    }

    public static String getFileNameByUrl(URL url) {
        String fileName = url.toString();
        return fileName.substring(fileName.lastIndexOf("/") + 1);
    }

    public static File getFileByUrl(Context context, URL url){
        return new File(context.getCacheDir(), getFileNameByUrl(url));
    }
}
