package com.myproject.novel.ui.auth.register;

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
import com.myproject.novel.local.util.customfonts.EditText_Poppins_Regular;
import com.myproject.novel.local.util.customfonts.MyTextView_Poppins_Medium;
import com.myproject.novel.model.RegisterRequestModel;
import com.myproject.novel.ui.auth.CallbackAuthActivity;

import org.jetbrains.annotations.NotNull;

public class RegisterFragment extends Fragment {

    private CallbackAuthActivity mCallback;

    private RegisterViewModel mViewModel;
    private View rootView;
    private EditText_Poppins_Regular emailInput, nicknameInput, passwordInput, rePasswordInput;
    private MyTextView_Poppins_Medium btnRegister;
    private Toast toast;
    private InputMethodManager imm;
    private ProgressBar progressBar;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.register_fragment, container, false);

        MyTextView_Poppins_Medium goToLogin = rootView.findViewById(R.id.goto_login);

        btnRegister = rootView.findViewById(R.id.btn_register);

        emailInput = rootView.findViewById(R.id.email);
        nicknameInput = rootView.findViewById(R.id.nickname);
        passwordInput = rootView.findViewById(R.id.pass);
        rePasswordInput = rootView.findViewById(R.id.re_pass);


        progressBar = rootView.findViewById(R.id.loading_submit);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor(getString(R.string.popular_color)), android.graphics.PorterDuff.Mode.MULTIPLY);

        imm = (InputMethodManager) requireActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(emailInput, 0);
        imm.showSoftInput(nicknameInput, 0);
        imm.showSoftInput(passwordInput, 0);
        imm.showSoftInput(rePasswordInput, 0);


        btnRegister.setOnClickListener(v -> register());

        goToLogin.setOnClickListener(v -> mCallback.startLoginFragment());

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
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


    private void register() {
        emailInput.clearFocus();
        nicknameInput.clearFocus();
        passwordInput.clearFocus();
        rePasswordInput.clearFocus();
        btnRegister.setClickable(false);
        String emailTxt = emailInput.getText().toString();
        String nickNameTxt = nicknameInput.getText().toString();
        String passwordTxt = passwordInput.getText().toString();
        String rePasswordTxt = rePasswordInput.getText().toString();

        imm.hideSoftInputFromWindow(rePasswordInput.getWindowToken(), 0);

        if (!emailTxt.equals("") && !passwordTxt.equals("") && !nickNameTxt.equals("")) {

            progressBar.setVisibility(View.VISIBLE);

            RegisterRequestModel registerRequestModel = new RegisterRequestModel(emailTxt, nickNameTxt, passwordTxt);

            mViewModel.register(registerRequestModel);

            mViewModel.userModel.observe(getViewLifecycleOwner(), res -> {

                btnRegister.setClickable(true);

                if (res != null) {

                    toast = Toast.makeText(requireContext(), getString(R.string.register_success_txt), Toast.LENGTH_SHORT);
                    toast.show();
                    mCallback.startLoginFragment();


                } else {
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        toast = Toast.makeText(requireContext(), getString(R.string.register_fail_txt), Toast.LENGTH_SHORT);
                        toast.show();
                    }, 500);

                }

            });
        }
    }


}