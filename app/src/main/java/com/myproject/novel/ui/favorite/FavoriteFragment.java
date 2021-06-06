package com.myproject.novel.ui.favorite;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.SharedPreferencesUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.ListNovelModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.favorite.epoxy.FavoriteController;
import com.myproject.novel.ui.filter.LoadMoreRecyclerViewScrollListener;
import com.myproject.novel.ui.novel.NovelActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class FavoriteFragment extends Fragment implements FavoriteController.EpoxyAdapterCallbacks {

    private FavoriteViewModel mViewModel;
    private FavoriteController favoriteController;
    private View rootView;
    private int startPage = 1;
    private String accessToken;
    private boolean isLogin;
    private ListNovelModel storeData;
    private LoadMoreRecyclerViewScrollListener loadMoreScrollListener;

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.favorite_fragment, container, false);
        setController(requireContext());
        isLogin = (boolean) SharedPreferencesUtils.getParam(requireContext(), UC.IS_USER_LOGGED_IN, false);
        accessToken = (String) SharedPreferencesUtils.getParam(requireContext(), UC.ACCESS_TOKEN, "");
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new FavoriteViewModel(accessToken);

        ListNovelModel listNovelModel = new ListNovelModel();
        listNovelModel.setData(new ArrayList<>());

        if (isLogin) {

            observeData();

            mViewModel.novelList.observe(getViewLifecycleOwner(), res -> {
                if (res == null || res.getData() == null || res.getData().isEmpty()) {
                    loadMoreScrollListener.setHasMoreToLoad(false);
                    favoriteController.setListNovelModel(listNovelModel);
                } else {
                    if (storeData == null) storeData = new ListNovelModel();

                    double totalPage = (double) res.getMeta().getTotal() / res.getMeta().getLimit();

                    if (startPage == (int) Math.ceil(totalPage)) {

                        loadMoreScrollListener.setHasMoreToLoad(false);
                    }

                    ArrayList<NovelModel> novelModels = (ArrayList<NovelModel>) storeData.getData();

                    novelModels.addAll(0, res.getData());

                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        storeData.setData(novelModels);
                        storeData.setMeta(res.getMeta());
                        favoriteController.setListNovelModel(storeData);
                        startPage++;
                    }, 500);
                }

            });

        } else {

            favoriteController.setListNovelModel(listNovelModel);
        }
    }

    private void observeData() {
        Log.e("current call", String.valueOf(startPage));
        mViewModel.fetchNovelList(startPage);
    }

    private void setController(Context ctx) {
        if (ctx != null) {

            RecyclerView favoriteRecyclerView = rootView.findViewById(R.id.favorite_list);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx);

            loadMoreScrollListener = new LoadMoreRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void fetchNextPage() {
                    observeData();
                }
            };

            favoriteRecyclerView.addOnScrollListener(loadMoreScrollListener);

            favoriteRecyclerView.setLayoutManager(linearLayoutManager);

            favoriteController = new FavoriteController(this, loadMoreScrollListener, startPage);

            favoriteController.setDebugLoggingEnabled(true);

            favoriteRecyclerView.setAdapter(favoriteController.getAdapter());
        }

    }


    @Override
    public void novelTitleClick(NovelModel model) {
        HashMap<String, String> data = new HashMap<>();
        data.put(UC.NOVEL_ID, String.valueOf(model.getNovelId()));
        CommonUtils.startActivity(requireActivity(), NovelActivity.class, data);
    }

    @Override
    public void onResume() {
        super.onResume();

        isLogin = (boolean) SharedPreferencesUtils.getParam(requireContext(), UC.IS_USER_LOGGED_IN, false);
        accessToken = (String) SharedPreferencesUtils.getParam(requireContext(), UC.ACCESS_TOKEN, "");
    }
}