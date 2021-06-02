package com.myproject.novel.local.db;

import android.content.Context;

import com.myproject.novel.local.util.UC;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Database {

    public static void initRealm(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(UC.DB_NAME)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm.setDefaultConfiguration(config);
    }


}
