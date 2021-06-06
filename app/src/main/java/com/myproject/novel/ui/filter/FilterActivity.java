package com.myproject.novel.ui.filter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.ListNovelModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.filter.epoxy.FilterController;
import com.myproject.novel.ui.main.MainActivity;
import com.myproject.novel.ui.novel.NovelActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FilterActivity extends AppCompatActivity implements FilterController.EpoxyAdapterCallbacks {

    private int tagId;
    private String tagName;
    private FilterViewModel filterViewModel;
    private FilterController filterController;
    private ListNovelModel storeData;
    private LoadMoreRecyclerViewScrollListener loadMoreScrollListener;
    private int startPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        loadData();
        loadViewModel();
        settingLoadMore();
        observeData();
        loadToolbar();
        TextView tagNameTxt = findViewById(R.id.tag_name);
        tagNameTxt.setText(tagName);

        filterViewModel.novelList.observe(this, res -> {

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
                filterController.setListNovelModel(storeData);
                startPage++;
            }, 500);
        });

    }

    private void loadToolbar() {
        Toolbar toolbar = findViewById(R.id.app_toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back_to_home_button);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void loadData() {
        Intent intent = getIntent();
        tagId = Integer.parseInt(intent.getStringExtra(UC.TAG_ID));
        tagName = intent.getStringExtra(UC.TAG_NAME);
    }

    private void loadViewModel() {
        filterViewModel = new FilterViewModel(tagId);

    }

    private void observeData() {
        Log.e("current call", String.valueOf(startPage));
        filterViewModel.fetchNovelList(startPage);
    }

    private void settingLoadMore() {
        RecyclerView recyclerView = findViewById(R.id.result_filter_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        loadMoreScrollListener = new LoadMoreRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void fetchNextPage() {
                observeData();
            }
        };
        recyclerView.addOnScrollListener(loadMoreScrollListener);
        filterController = new FilterController(this, loadMoreScrollListener, 1);

        recyclerView.setAdapter(filterController.getAdapter());

    }

    @Override
    public void onBackPressed() {
        CommonUtils.startActivity(this, MainActivity.class, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void novelTitleClick(NovelModel model) {
        HashMap<String, String> data = new HashMap<>();
        data.put(UC.NOVEL_ID, String.valueOf(model.getNovelId()));
        CommonUtils.startActivity(this, NovelActivity.class, data);
    }


}