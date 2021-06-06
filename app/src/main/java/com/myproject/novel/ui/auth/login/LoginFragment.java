package com.myproject.novel.ui.auth.login;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.novel.R;
import com.myproject.novel.local.util.SharedPreferencesUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.local.util.customfonts.EditText_Poppins_Regular;
import com.myproject.novel.local.util.customfonts.MyTextView_Poppins_Medium;
import com.myproject.novel.model.LoginRequestModel;
import com.myproject.novel.ui.auth.CallbackAuthActivity;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment {

    private CallbackAuthActivity mCallback;

    private LoginViewModel mViewModel;

    private View rootView;

    private Toast toast;

    private InputMethodManager imm;

    private ProgressBar progressBar;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.login_fragment, container, false);

        EditText_Poppins_Regular email = rootView.findViewById(R.id.username_input);
        EditText_Poppins_Regular password = rootView.findViewById(R.id.pass);
        MyTextView_Poppins_Medium submitLogin = rootView.findViewById(R.id.login_btn);
        MyTextView_Poppins_Medium goToRegister = rootView.findViewById(R.id.go_register);
        MyTextView_Poppins_Medium forgotPassword = rootView.findViewById(R.id.forgot_password);

        progressBar = rootView.findViewById(R.id.loading_submit);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor(getString(R.string.popular_color)), android.graphics.PorterDuff.Mode.MULTIPLY);

        imm = (InputMethodManager) requireActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(email, 0);
        imm.showSoftInput(password, 0);
        submitLogin.setOnClickListener(v -> login(email, password, submitLogin));
        goToRegister.setOnClickListener(v -> mCallback.startRegisterFragment());
        forgotPassword.setOnClickListener(v -> mCallback.startForgotFragment());

        return rootView;
    }

    private void login(EditText_Poppins_Regular email, EditText_Poppins_Regular password, MyTextView_Poppins_Medium submitLogin) {
        email.clearFocus();
        password.clearFocus();
        submitLogin.setClickable(false);
        String emailTxt = email.getText().toString();
        String passwordTxt = password.getText().toString();
        imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
        if (!emailTxt.equals("") && !passwordTxt.equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            LoginRequestModel loginRequestModel = new LoginRequestModel(emailTxt, passwordTxt);

            mViewModel.login(loginRequestModel);

            mViewModel.authModel.observe(getViewLifecycleOwner(), res -> {

                submitLogin.setClickable(true);

                if (res.getCode() == 200) {

                    SharedPreferencesUtils.setParam(requireContext(), UC.ACCESS_TOKEN, res.getAccessToken());
                    SharedPreferencesUtils.setParam(requireContext(), UC.IS_USER_LOGGED_IN, true);

                    mViewModel.getInfo(res.getAccessToken());
                    mViewModel.profileModel.observe(getViewLifecycleOwner(), me -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        SharedPreferencesUtils.setParam(requireContext(), UC.EMAIL, emailTxt);
                        SharedPreferencesUtils.setParam(requireContext(), UC.NICKNAME, me.getNickname());
                        SharedPreferencesUtils.setParam(requireContext(), UC.BIRTH_DAY, me.getBirthday());
                        SharedPreferencesUtils.setParam(requireContext(), UC.GENDER, me.getGender());
                        SharedPreferencesUtils.setParam(requireContext(), UC.AVATAR, me.getAvatar());

                        toast = Toast.makeText(requireContext(), getString(R.string.login_success_txt), Toast.LENGTH_SHORT);
                        toast.show();
                        mCallback.onBackPressed();
                    });

                } else {
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        toast = Toast.makeText(requireContext(), getString(R.string.login_fail_txt), Toast.LENGTH_SHORT);
                        toast.show();
                    }, 500);

                }

            });
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel

    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof CallbackAuthActivity) {
            mCallback = (CallbackAuthActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackMainActivity");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}