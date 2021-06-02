package com.myproject.novel.local.db.handle;

import com.myproject.novel.model.Model;

import java.util.HashMap;

import io.realm.RealmObject;

public interface Handle {

    RealmObject get(HashMap<String, String> query);

    RealmObject create(Model obj);

    RealmObject update(int id, Model obj);

    boolean delete(int id);
}
