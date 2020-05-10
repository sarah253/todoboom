package com.example.todoboom;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


public class MyCustomApp extends Application {
    private static final String LIST_SIZE = "LIST_SIZE";
    private static Context app_context;

    @Override
    public void onCreate() {
        super.onCreate();
        app_context = getApplicationContext();
        TodoListSize();
    }

    public static void TodoListSize(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(app_context);
        Log.i("todo list size", String.valueOf(sp.getInt(LIST_SIZE,0)));
    }
}
