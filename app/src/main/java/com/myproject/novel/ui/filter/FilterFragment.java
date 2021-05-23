package com.myproject.novel.ui.filter;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.model.TagModel;
import com.myproject.novel.ui.filter.epoxy.FilterController;
import com.myproject.novel.CallbackMainActivity;
import com.myproject.novel.ui.novel.NovelActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FilterFragment extends Fragment implements FilterController.EpoxyAdapterCallbacks {

    private CallbackMainActivity mCallback;
    private FilterViewModel mViewModel;
    private View rootView;
    private RecyclerView recyclerView;
    private FilterController filterController;
    boolean isSetBg = false;
    private NestedScrollView nestedScrollView;

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CommonUtils.setFullScreenWithStatusBar(requireActivity(),true);
        rootView = inflater.inflate(R.layout.filter_fragment, container, false);
        nestedScrollView = rootView.findViewById(R.id.explode_nested_scroll_view);
        setController(requireContext());
        loadNestedScrollView(requireActivity());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FilterViewModel.class);
        filterController.setData(dump());
    }

    private void setController(Context ctx) {
        if(ctx != null) {
            filterController = new FilterController(this);
            filterController.setDebugLoggingEnabled(true);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.filter_recyclerview);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(filterController.getAdapter());
        }
    }


    @Override
    public void tagClick(TagModel model) {
        mCallback.startActivity(NovelActivity.class,null);
    }
    
    private List<TagModel> dump() {
        List<TagModel> novelModelList = new ArrayList<>();


        novelModelList.add(new TagModel(1,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Đại Mộng Đương Giác",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/4784170c47.jpg-posterend4"
        ));
        novelModelList.add(new TagModel(2,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Duẫn",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/3057297ff5.jpg-posterend4"

        ));
        novelModelList.add(new TagModel(3,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Ôn Thụy An",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/79804526d6.jpg-posterend4"
        ));
        novelModelList.add(new TagModel(4,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Đại Mộng Đương Giác",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/366260a153.jpg-posterend4"
        ));
        novelModelList.add(new TagModel(5,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Duẫn",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/7604317d88.jpg"

        ));
        novelModelList.add(new TagModel(6,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Ôn Thụy An",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/5417029f18.jpg-posterend4"
        ));
        return novelModelList;
    }

    private void loadNestedScrollView(Activity activity) {
        CommonUtils.clearLightStatusBar(activity);
        mCallback.changeTabLayoutColor("#4896f0","#323131");
        mCallback.changeToolbarBackgroundColor("#FFFFFF","#FFFFFF",0);
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