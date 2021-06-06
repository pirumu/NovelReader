package com.myproject.novel.ui.bookstore;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.ui.favorite.FavoriteFragment;
import com.myproject.novel.ui.main.CallbackMainActivity;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class BookStoreFragment extends Fragment {

    private CallbackMainActivity mCallback;

    private BookStoreViewModel mViewModel;

    private View rootView;

    public static BookStoreFragment newInstance() {
        return new BookStoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.book_store_fragment, container, false);
        CommonUtils.setFullScreenWithStatusBar(requireActivity(), true);
        loadNestedScrollView(requireActivity());

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BookStoreViewModel.class);
        // TODO: Use the ViewModel
    }

    private void loadNestedScrollView(Activity activity) {
        CommonUtils.clearLightStatusBar(activity);
        mCallback.changeTabLayoutColor(getString(R.string.popular_color), getString(R.string.tab_layout_color));
        mCallback.changeToolbarBackgroundColor(getString(R.string.white_color), getString(R.string.white_color), 0);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof CallbackMainActivity) {
            mCallback = (CallbackMainActivity) context;
            mCallback.showTabBookStore();
            mCallback.loadFragment(new FavoriteFragment());
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackMainActivity");
        }
    }
}