package com.myproject.novel.ui.user;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.novel.R;
import com.myproject.novel.local.util.SharedPreferencesUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.ui.auth.AuthActivity;
import com.myproject.novel.ui.main.CallbackMainActivity;

import org.jetbrains.annotations.NotNull;

public class UserFragment extends Fragment {

    private CallbackMainActivity mCallback;
    private UserViewModel mViewModel;
    private View rootView;
    private  ActionBar actionBar;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.user_fragment, container, false);

        actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onAttach(@NotNull Context context) {
        boolean  isLogin = (boolean) SharedPreferencesUtils.getParam(requireContext(), UC.IS_USER_LOGGED_IN, false);
        super.onAttach(context);

        if (context instanceof CallbackMainActivity) {
            mCallback = (CallbackMainActivity) context;
            if (!isLogin) {
                mCallback.startActivity(AuthActivity.class, null);
            }
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackMainActivity");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(actionBar!=null) {
            actionBar.hide();
        }

    }
    @Override
    public void onStop() {
        super.onStop();
        if(actionBar!=null) {
            actionBar.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(actionBar!=null) {
            actionBar.show();
        }
    }
}