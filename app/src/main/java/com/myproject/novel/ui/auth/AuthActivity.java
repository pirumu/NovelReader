package com.myproject.novel.ui.auth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.SharedPreferencesUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.ui.auth.forgot.ForgotFragment;
import com.myproject.novel.ui.auth.intro.IntroFragment;
import com.myproject.novel.ui.auth.login.LoginFragment;
import com.myproject.novel.ui.auth.register.RegisterFragment;
import com.myproject.novel.ui.main.MainActivity;

public class AuthActivity extends AppCompatActivity implements CallbackAuthActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtils.setFullScreen(this, true);
        setContentView(R.layout.activity_auth);
        startIntroFragment(savedInstanceState);
    }

    private void startIntroFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.auth_fragment_container_view, IntroFragment.class, null)
                    .commit();
        }
    }

    public void startLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.auth_fragment_container_view, loginFragment);
        transaction.commit();
    }

    public void startRegisterFragment() {
        RegisterFragment registerFragment = new RegisterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.auth_fragment_container_view, registerFragment);
        transaction.commit();
    }


    public void startForgotFragment() {
        ForgotFragment forgotFragment = new ForgotFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.auth_fragment_container_view, forgotFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

        String classNameShare = (String) SharedPreferencesUtils.getParam(getApplicationContext(), UC.ACTIVITY_BEFORE, MainActivity.class.toString());
        int keyId = (int) SharedPreferencesUtils.getParam(getApplicationContext(), UC.DATA_ACTIVITY_BEFORE, 1);
        try {
            CommonUtils.startActivity(this, classNameShare.equals("MainActivity") ? MainActivity.class : Class.forName(classNameShare), CommonUtils.buildData(classNameShare, String.valueOf(keyId)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}