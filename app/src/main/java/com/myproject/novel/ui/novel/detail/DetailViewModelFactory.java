package com.myproject.novel.ui.novel.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class DetailViewModelFactory implements ViewModelProvider.Factory {
    private final int mParam;
    private final String auth;

    public DetailViewModelFactory(int _param, String _auth) {
        mParam = _param;
        auth = _auth;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(mParam, auth);
    }
}
