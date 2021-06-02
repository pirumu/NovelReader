package com.myproject.novel.local.db.handle;

import com.myproject.novel.model.Model;

import java.util.HashMap;

import io.realm.RealmObject;

public class NovelHandle implements Handle {
    @Override
    public RealmObject get(HashMap<String, String> query) {
        return null;
    }

    @Override
    public RealmObject create(Model obj) {
        return null;
    }

    @Override
    public RealmObject update(int id, Model obj) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
