package com.myproject.novel.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.ui.auth.login.LoginFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtils.setFullScreen(this,true);
        setContentView(R.layout.activity_auth);
        startLoginFragment(savedInstanceState);
    }

    private void startLoginFragment(Bundle savedInstanceState) {
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .add(R.id.auth_fragment_container_view, LoginFragment.class, null)
//                    .commit();
//        }
    }
}