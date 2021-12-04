package com.example.fragmentproject.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public static void userToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
