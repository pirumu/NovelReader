package com.myproject.novel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.ui.auth.AuthActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtils.setFullScreen(this,true);
        setContentView(R.layout.activity_splash);
        showLoading();
        checkBeforeStart();
    }

    private void showLoading() {
        LinearLayout loadingView = findViewById(R.id.loading);
        loadingView.setVisibility(View.VISIBLE);
    }
    private void checkBeforeStart() {
        new Handler(Looper.getMainLooper()).postDelayed(this::openMainActivity, 1000);
    }
    private void openAuthActivity() {
        startActivity(new Intent(SplashActivity.this, AuthActivity.class));
        finish();
    }

    private void openMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

}