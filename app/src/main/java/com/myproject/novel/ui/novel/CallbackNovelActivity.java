package com.myproject.novel.ui.novel;

import java.util.HashMap;

public interface CallbackNovelActivity {

    void startActivity(Class<?> className, HashMap<String, String> data);
    void needLogin();
}
