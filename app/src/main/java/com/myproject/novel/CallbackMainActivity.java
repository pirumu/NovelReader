package com.myproject.novel;

import java.util.HashMap;

public interface CallbackMainActivity {

    public void changeTabLayoutColor(String indicatorColor,String textColor);

    public void changeToolbarBackgroundColor(String startColor, String endColor,int duration);

    public void loadToolBar();

    public void startActivity(Class<?> className, HashMap<String,String> data);

    public void destroyTabLayout();
}
