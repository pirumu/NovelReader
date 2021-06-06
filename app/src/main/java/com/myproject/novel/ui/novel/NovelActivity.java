package com.myproject.novel.ui.novel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.GlideApp;
import com.myproject.novel.local.util.GlideRequest;
import com.myproject.novel.local.util.SharedPreferencesUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.ChapterModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.auth.AuthActivity;
import com.myproject.novel.ui.main.MainActivity;
import com.myproject.novel.ui.novel.detail.DetailViewModel;
import com.myproject.novel.ui.novel.detail.DetailViewModelFactory;
import com.snakydesign.livedataextensions.Lives;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.glide.transformations.BlurTransformation;

@SuppressWarnings("deprecation")
public class NovelActivity extends AppCompatActivity implements CallbackNovelActivity {

    private DetailViewModel mViewModel;
    private TabLayout tabLayout;
    private Boolean likeStatus = false;
    private ImageView novelBackgroundGradient;
    private AppCompatImageView novelBackgroundImage;
    private CollapsingToolbarLayout collapsingToolbar;
    private int novelId;
    private boolean isLogin = false;
    private String accessToken;

    private TextView novelTitle, author, tag, status, read, like;
    private AppCompatButton acButton, startReadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel);

        // load  SharedPreferences
        isLogin = (boolean) SharedPreferencesUtils.getParam(this.getApplicationContext(), UC.IS_USER_LOGGED_IN, false);
        accessToken = (String) SharedPreferencesUtils.getParam(this.getApplicationContext(), UC.ACCESS_TOKEN, "");

        tag = findViewById(R.id.tag);
        read = findViewById(R.id.read);
        like = findViewById(R.id.like);
        author = findViewById(R.id.author);
        status = findViewById(R.id.status);
        novelTitle = findViewById(R.id.novel_title);
        acButton = findViewById(R.id.favorite_now_btn);
        startReadButton = findViewById(R.id.start_read);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        novelBackgroundImage = findViewById(R.id.novel_background_image);
        novelBackgroundGradient = findViewById(R.id.novel_background_gradian);

        acButton.setClickable(false);
        startReadButton.setClickable(false);

        loadTab();
        loadData();
        loadViewModel();
        viewModelObserver();
        Toolbar toolbar = findViewById(R.id.novel_toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.arrow);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void loadControlFragment(NovelModel novelModel, List<ChapterModel> list) {

        ViewPager2 viewPager = findViewById(R.id.novel_pager);
        NovelAdapter novelAdapter = new NovelAdapter(this, novelModel, list);
        viewPager.setAdapter(novelAdapter);
        viewPager.setOffscreenPageLimit(2);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setCustomView(setUpCustomTab(R.string.novel_detail_txt));
                    } else {
                        tab.setCustomView(setUpCustomTab(R.string.chapter_txt));
                    }
                }
        ).attach();
    }

    private void loadData() {
        Intent intent = getIntent();
        novelId = Integer.parseInt(intent.getStringExtra(UC.NOVEL_ID));
    }

    private void viewModelObserver() {

        Lives.zip(mViewModel.novelModel, mViewModel.listChapter, Pair::new)
                .observe(this, res -> {
                    NovelModel novel = res.first;
                    ArrayList<ChapterModel> chapters = (ArrayList<ChapterModel>) res.second;
                    acButton.setClickable(true);
                    startReadButton.setClickable(true);
                    setBackgroundDefault(novel.getNovelCoverPhotoUrl());
                    novelTitle.setText(novel.getNovelTitle());
                    String authorName = novel.getAuthor() == null ? getString(R.string.author_ta_txt) : novel.getAuthor().getAuthorName();
                    author.setText(authorName);
                    String tagName = novel.getTag() == null ? getString(R.string.example_txt) : novel.getTag().getTagName();
                    tag.setText(tagName);
                    String st = novel.getNovelStatus() > UC.DEFAULT_STATUS ? getString(R.string.end_txt) : getString(R.string.update_txt);
                    status.setText(st);
                    read.setText(getString(R.string.defaut_read_txt));
                    read.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.read), null, null, null);
                    like.setText(novel.getNovelLike());
                    like.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.heart), null, null, null);
                    likeStatus = novel.getUserLiked();
                    if (novel.getUserLiked()) {
                        unlikeNovel();
                    } else {
                        likeNovel();
                    }
                    if (!chapters.isEmpty()) {
                        startReadButton.setOnClickListener(v -> {
                            HashMap<String, String> data = new HashMap<>();
                            data.put(UC.NOVEL_ID, String.valueOf(novel.getNovelId()));
                            data.put(UC.CHAPTER_ID, String.valueOf(chapters.get(0).getChapterId()));
                            CommonUtils.startActivity(NovelActivity.this, ReadActivity.class, data);
                        });
                    }
                    loadControlFragment(novel, chapters);
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        accessToken = (String) SharedPreferencesUtils.getParam(this.getApplicationContext(), UC.ACCESS_TOKEN, "");
        isLogin = (boolean) SharedPreferencesUtils.getParam(this.getApplicationContext(), UC.IS_USER_LOGGED_IN, false);
    }

    public void setBackgroundDefault(String url) {

        GlideRequest<Drawable> bgDrawable =
                GlideApp.with(getApplicationContext())
                        .load(url)
                        .placeholder(CommonUtils.shimmerEffect())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .diskCacheStrategy(DiskCacheStrategy.ALL);

        bgDrawable.into(novelBackgroundImage);

        bgDrawable.apply(new RequestOptions().transform(new BlurTransformation(UC.BLUR)))
                .into(novelBackgroundGradient);

        GlideApp.with(this)
                .asBitmap().load(url)
                .placeholder(CommonUtils.shimmerEffect())
                .apply(new RequestOptions().transform(new BlurTransformation(UC.BLUR)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NotNull Bitmap bitmap, Transition<? super Bitmap> transition) {
                        collapsingToolbar.setContentScrim(new BitmapDrawable(bitmap));
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                    }
                });

    }

    private void loadTab() {
        tabLayout = findViewById(R.id.novel_tab);
        tabLayout.removeAllTabs();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tabActive = (TextView) tab.getCustomView();
                if (tabActive != null) {
                    tabActive.setTextSize(UC.DEFAULT_TEXT_SIZE);
                    tabActive.setTextColor(Color.parseColor(getString(R.string.popular_color)));

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tabActive = (TextView) tab.getCustomView();
                if (tabActive != null) {
                    tabActive.setTextSize(UC.DEFAULT_TEXT_SIZE);
                    tabActive.setTextColor(getResources().getColor(R.color.novel_tab_color));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @SuppressLint("InflateParams")
    private TextView setUpCustomTab(int textId) {
        TextView customTab = (TextView) getLayoutInflater().inflate(R.layout.custom_tab, null);
        customTab.setTextColor(Color.parseColor(getString(R.string.popular_color)));
        customTab.setText(textId);
        customTab.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        customTab.setTextColor(getResources().getColor(R.color.novel_tab_color));
        customTab.setLetterSpacing(-0.05f);
        return customTab;


    }


    private String getClassName() {
        String className = NovelActivity.class.toString();
        return className.replaceAll(UC.CLASS_REPLACE, "");
    }

    public void needLogin() {
        SharedPreferencesUtils.setParam(getApplicationContext(), UC.ACTIVITY_BEFORE, getClassName());
        SharedPreferencesUtils.setParam(getApplicationContext(), UC.DATA_ACTIVITY_BEFORE, novelId);
        CommonUtils.startActivity(this, AuthActivity.class, null);
    }

    public void favoriteNovel(View view) {

        if (!isLogin) {

            needLogin();
//            new AlertDialog.Builder(getApplicationContext())
//                    .setTitle(getString(R.string.login_txt))
//                    .setMessage(R.string.must_login_txt)
//                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) ->   ())
//                    .setNegativeButton(android.R.string.no,null).show();

        } else {
            this.mViewModel.likeNovel(accessToken, novelId);
            if (likeStatus) {
                this.mViewModel.like.observe(this, res -> {
                    if (res) {
                        likeStatus = false;
                    } else {
                        unlikeNovel();
                    }
                });
                likeNovel();
            } else {
                this.mViewModel.like.observe(this, res -> {
                    if (res) {
                        likeStatus = true;
                    } else {
                        likeNovel();
                    }
                });
                unlikeNovel();
            }
        }
    }

    private void unlikeNovel() {
        acButton.setText(R.string.unlike_txt);
        acButton.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplicationContext(), R.drawable.heartcheck), null, null);
    }

    private void likeNovel() {
        acButton.setText(R.string.like_txt);
        acButton.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplicationContext(), R.drawable.heartplus), null, null);
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideSystemUI();

    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void loadViewModel() {
        mViewModel = new ViewModelProvider(this, new DetailViewModelFactory(0, novelId, accessToken)).get(DetailViewModel.class);
    }

    @Override
    public void reCreateActivity() {
        mViewModel.fetchNovelDetail(accessToken, novelId);
        mViewModel.fetchListChapter(novelId);
    }
}