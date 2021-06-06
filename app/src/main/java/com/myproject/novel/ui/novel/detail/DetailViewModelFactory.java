package com.myproject.novel.ui.novel.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class DetailViewModelFactory implements ViewModelProvider.Factory {
    private final int tagId;
    private final int novelId;
    private final String auth;

    public DetailViewModelFactory(int _tagId, int _param, String _auth) {
        tagId = _tagId;
        novelId = _param;
        auth = _auth;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(tagId, novelId, auth);
    }
}
