package com.example.pojochat;

import android.content.Context;

public class Application extends android.app.Application{
    public static Context getInstance;

    @Override
    public Context getApplicationContext() {
       getInstance= super.getApplicationContext();
       return getInstance;
    }
}