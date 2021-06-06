package com.myproject.novel.ui.start;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtils.setFullScreen(this, true);
        setContentView(R.layout.activity_splash);
        showLoading();
        checkBeforeStart();
    }

    private void showLoading() {
        LinearLayout loadingView = findViewById(R.id.loading);
        loadingView.setVisibility(View.VISIBLE);
    }

    private void checkBeforeStart() {
        new Handler(Looper.getMainLooper()).postDelayed(this::openMainActivity, UC.DELAY);
    }


    private void openMainActivity() {
        CommonUtils.startActivity(this, MainActivity.class, null);
    }

}