package com.myproject.novel.ui.tag;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.TagModel;
import com.myproject.novel.ui.filter.FilterActivity;
import com.myproject.novel.ui.main.CallbackMainActivity;
import com.myproject.novel.ui.tag.epoxy.TagController;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class TagFragment extends Fragment implements TagController.EpoxyAdapterCallbacks {

    private CallbackMainActivity mCallback;
    private TagViewModel mViewModel;
    private View rootView;
    private RecyclerView recyclerView;
    private TagController filterController;
    boolean isSetBg = false;
    private NestedScrollView nestedScrollView;

    public static TagFragment newInstance() {
        return new TagFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CommonUtils.setFullScreenWithStatusBar(requireActivity(), true);
        rootView = inflater.inflate(R.layout.tag_fragment, container, false);
        nestedScrollView = rootView.findViewById(R.id.explode_nested_scroll_view);
        setController(requireContext());
        loadNestedScrollView(requireActivity());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TagViewModel.class);
        mViewModel.tagModelList.observe(getViewLifecycleOwner(), res -> filterController.setData(res));
    }

    private void setController(Context ctx) {
        if (ctx != null) {
            filterController = new TagController(this);
            filterController.setDebugLoggingEnabled(true);
            recyclerView = rootView.findViewById(R.id.filter_recyclerview);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(filterController.getAdapter());
        }
    }


    @Override
    public void tagClick(TagModel model) {
        HashMap<String, String> data = new HashMap<>();
        data.put(UC.TAG_ID, String.valueOf(model.getTagId()));
        data.put(UC.TAG_NAME, model.getTagName());
        CommonUtils.startActivity(requireActivity(), FilterActivity.class, data);
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
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackMainActivity");
        }
    }
}