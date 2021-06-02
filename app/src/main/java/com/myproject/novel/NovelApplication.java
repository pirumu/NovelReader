package com.myproject.novel;

import android.app.Application;

import com.myproject.novel.local.db.Database;

public class NovelApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Database.initRealm(this);
    }
}
