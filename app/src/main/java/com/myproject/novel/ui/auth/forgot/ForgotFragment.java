package com.myproject.novel.ui.auth.forgot;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.novel.R;
import com.myproject.novel.local.util.customfonts.EditText_Poppins_Regular;
import com.myproject.novel.local.util.customfonts.MyTextView_Poppins_Medium;
import com.myproject.novel.model.ForgotPasswordModel;
import com.myproject.novel.ui.auth.CallbackAuthActivity;

import org.jetbrains.annotations.NotNull;

public class ForgotFragment extends Fragment {

    private ForgotViewModel mViewModel;
    private EditText_Poppins_Regular emailInput;
    private MyTextView_Poppins_Medium resetBtn;
    private Toast toast;

    private InputMethodManager imm;

    private ProgressBar progressBar;

    private View rootView;

    private CallbackAuthActivity mCallback;

    public static ForgotFragment newInstance() {
        return new ForgotFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.forgot_fragment, container, false);
        resetBtn = rootView.findViewById(R.id.reset_btn);
        emailInput = rootView.findViewById(R.id.email);
        progressBar = rootView.findViewById(R.id.loading_submit);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor(getString(R.string.popular_color)), android.graphics.PorterDuff.Mode.MULTIPLY);

        imm = (InputMethodManager) requireActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(emailInput, 0);

        resetBtn.setOnClickListener(v -> resetPassword());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ForgotViewModel.class);
        // TODO: Use the ViewModel
    }

    private void resetPassword() {
        emailInput.clearFocus();
        resetBtn.setClickable(false);

        String emailTxt = emailInput.getText().toString();

        imm.hideSoftInputFromWindow(emailInput.getWindowToken(), 0);

        if (!emailTxt.equals("")) {

            progressBar.setVisibility(View.VISIBLE);

            ForgotPasswordModel forgotPasswordModel = new ForgotPasswordModel(emailTxt);

            mViewModel.resetPassword(forgotPasswordModel);

            mViewModel.rsp.observe(getViewLifecycleOwner(), res -> {
                progressBar.setVisibility(View.GONE);
                resetBtn.setClickable(true);

                if (res != null && res.code == 200) {

                    new AlertDialog.Builder(requireContext())
                            .setTitle(getString(R.string.info_txt))
                            .setMessage(res.msg)
                            .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> mCallback.startLoginFragment()).show();


                } else if (res != null) {
                    new AlertDialog.Builder(requireContext())
                            .setTitle(getString(R.string.info_txt))
                            .setMessage(res.msg)
                            .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> dialog.dismiss()).show();
                } else {
                    new AlertDialog.Builder(requireContext())
                            .setTitle(getString(R.string.info_txt))
                            .setMessage(R.string.something_error_txt)
                            .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> dialog.dismiss()).show();
                }

            });
        }
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

}