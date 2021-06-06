package com.myproject.novel.local.db.handle;

import java.util.HashMap;

import io.realm.RealmObject;

public interface Handle {

    RealmObject get(HashMap<String, String> query);

    RealmObject create();

    RealmObject update(int id);

    boolean delete(int id);
}
