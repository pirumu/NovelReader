package com.myproject.novel.ui.search;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.novel.NovelActivity;
import com.myproject.novel.ui.search.epoxy.SearchController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity implements SearchController.EpoxyAdapterCallbacks {
    private SearchView searchView;
    private String[] strArrData = {UC.NO_RESULT};
    private SearchViewModel mViewModel;
    private List<NovelModel> listData;
    private SearchController searchController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        listData = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        loadToolBar();
        settingViewModel();
        searchController = new SearchController(this);
        RecyclerView recyclerView = findViewById(R.id.result_search_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(searchController.getAdapter());

    }

    private void styleSuggestion(SearchView sv) {
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.auto_complete);
        autoComplete.setTextSize(14);
        autoComplete.setElevation(0);
        autoComplete.setBackgroundColor(Color.TRANSPARENT);
        autoComplete.setDropDownBackgroundResource(R.drawable.suggest_bg);
        autoComplete.setAdapter(sv.getSuggestionsAdapter());

    }

    @SuppressLint("CheckResult")
    public void loadToolBar() {
        //tool bar
        Toolbar myToolbar = findViewById(R.id.search_toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);

        //search view
        final String[] from = new String[]{UC.NOVEL_TITLE};
        final int[] to = new int[]{R.id.suggest_item};
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(SearchActivity.this,
                R.layout.suggest_item, null,
                from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        searchView = (SearchView) findViewById(R.id.search_box);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSuggestionsAdapter(simpleCursorAdapter);
        styleSuggestion(searchView);
        searchView.setQueryHint(getString(R.string.search_now_txt));
        RxSearchObservable.fromView(searchView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(text -> !text.isEmpty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    AtomicBoolean searchNow = new AtomicBoolean(true);
                    if (listData != null) {
                        listData.forEach(i -> {
                            if (i.getNovelTitle().equals(result)) {
                                searchNow.set(false);
                            }
                        });
                    }
                    if (searchNow.get()) {

                        startSearch(result.replace(UC.IS_SUBMIT, ""));
                    }

                    mViewModel.novelList.observe(SearchActivity.this, res -> {
                        if (result.contains(UC.IS_SUBMIT)) {
                            searchController.setData(listData);
                        }
                        if (res != null) {
                            listData = res;
                            ArrayList<String> dataList = new ArrayList<>();
                            res.forEach(i -> dataList.add(i.getNovelTitle()));
                            strArrData = dataList.toArray(new String[res.size()]);
                            final MatrixCursor mc = new MatrixCursor(new String[]{BaseColumns._ID, UC.NOVEL_TITLE});
                            for (int i = 0; i < strArrData.length; i++) {
                                mc.addRow(new Object[]{i, strArrData[i]});
                            }
                            simpleCursorAdapter.changeCursor(mc);
                        }
                    });

                });


        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {

                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                CursorAdapter ca = searchView.getSuggestionsAdapter();
                Cursor cursor = ca.getCursor();
                cursor.moveToPosition(position);
                searchView.setQuery(cursor.getString(cursor.getColumnIndex(UC.NOVEL_TITLE)), false);
                NovelModel novelModel = listData.get(position);
                List<NovelModel> one = new ArrayList<>();
                one.add(novelModel);
                searchController.setData(one);
                searchView.clearFocus();
                return true;
            }
        });


        ImageView clearButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        clearButton.setOnClickListener(v -> {
            if (searchView.getQuery().length() == 0) {
                searchView.setIconified(true);
            } else {
                searchView.setQuery("", false);
                searchController.setData(new ArrayList<>());
            }
        });

        TextView backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(r -> {
            super.onBackPressed();
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }
        }
    }

    private void startSearch(String novelTitle) {

        mViewModel.getCompositeDisposable().clear();
        mViewModel.search(novelTitle);
    }

    private void settingViewModel() {
        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Override
    public void novelTitleClick(NovelModel model) {
        HashMap<String, String> data = new HashMap<>();
        data.put(UC.NOVEL_ID, String.valueOf(model.getNovelId()));
        CommonUtils.startActivity(this, NovelActivity.class, data);
    }
}