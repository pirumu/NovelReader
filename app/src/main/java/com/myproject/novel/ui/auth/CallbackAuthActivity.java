package com.myproject.novel.ui.auth;

public interface CallbackAuthActivity {

    void startLoginFragment();

    void startRegisterFragment();

    void startForgotFragment();

    void onBackPressed();
}
