package com.example.kakeibo.activites.utils;


import android.util.Log;

import com.example.kakeibo.BuildConfig;

public class LogUtil {
    public static void debug(String tag, String message){
        if(BuildConfig.DEBUG){
            Log.d(tag, message);
        }
    }
}
