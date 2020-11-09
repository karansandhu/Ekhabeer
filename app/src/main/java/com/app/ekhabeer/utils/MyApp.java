package com.app.ekhabeer.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.app.ekhabeer.screens.ChoiceActivity;
import com.app.ekhabeer.screens.HomeActivity;

public class MyApp extends Application {

    String user_id;
    Context context;
    private static MyApp _instance;
    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        SharedPreferences prefs = this.getSharedPreferences(
                "Ekhabeer", Context.MODE_PRIVATE);
        user_id = prefs.getString("user_id","");
        Log.e("checkMyApp",">>" + user_id);
        if (user_id.equals("")){
            Intent intent = new Intent(getApplicationContext(), ChoiceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    public static MyApp getInstance() {
        return _instance;
    }
}
