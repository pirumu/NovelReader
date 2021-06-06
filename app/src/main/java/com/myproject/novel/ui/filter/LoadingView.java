package com.myproject.novel.ui.filter;

import android.view.View;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.myproject.novel.R;

@EpoxyModelClass
public abstract class LoadingView extends EpoxyModel<View> {
    @Override
    protected int getDefaultLayout() {
        return R.layout.item_loading;
    }
}