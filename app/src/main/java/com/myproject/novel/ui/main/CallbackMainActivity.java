package com.myproject.novel.ui.main;

import androidx.fragment.app.Fragment;

import java.util.HashMap;

public interface CallbackMainActivity {

    void changeTabLayoutColor(String indicatorColor, String textColor);

    void changeToolbarBackgroundColor(String startColor, String endColor, int duration);

    void loadToolBar();

    void startActivity(Class<?> className, HashMap<String, String> data);

    void destroyTabLayout();

    void showTabHome();

    void showTabBookStore();

    void loadFragment(Fragment fragment);
}
