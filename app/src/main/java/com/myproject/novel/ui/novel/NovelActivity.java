package com.myproject.novel.ui.novel;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
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
import com.myproject.novel.local.util.GlideApp;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class NovelActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private Boolean buttonActive = true;
    private ImageView novelBackgroundGradian;
    private AppCompatImageView novelBackgroundImage;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel);
        novelBackgroundGradian = (ImageView) findViewById(R.id.novel_background_gradian);
        novelBackgroundImage = (AppCompatImageView) findViewById(R.id.novel_background_image);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        loadTab();
        setBackgroundDefault("http://cn.e.pic.mangatoon.mobi/cartoon-posters/637534a96c.jpg");
        Toolbar toolbar = (Toolbar) findViewById(R.id.novel_toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.arrow);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ViewPager2 viewPager = findViewById(R.id.novel_pager);

        viewPager.setAdapter(new NovelAdapter(this));
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


    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_item, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void setBackgroundDefault(String url) {


        GlideApp.with(getApplicationContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).transition(
                DrawableTransitionOptions.withCrossFade())
                .into(novelBackgroundImage);

        GlideApp.with(getApplicationContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).transition(
                DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions().transform(new BlurTransformation(60)))
                .into(novelBackgroundGradian);

        GlideApp.with(this).asBitmap().load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .apply(new RequestOptions().transform(new BlurTransformation(60)))
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
                    tabActive.setTextSize(14);
                    tabActive.setTextColor(Color.parseColor("#4896f0"));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tabActive = (TextView) tab.getCustomView();
                if (tabActive != null) {
                    tabActive.setTextSize(14);
                    tabActive.setTextColor(getResources().getColor(R.color.novel_tab_color));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    private TextView setUpCustomTab(int textId) {
        TextView customTab = (TextView) getLayoutInflater().inflate(R.layout.custom_tab, null);
        customTab.setTextColor(Color.parseColor("#4896f0"));
        customTab.setText(textId);
        customTab.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        customTab.setTextColor(getResources().getColor(R.color.novel_tab_color));
        customTab.setLetterSpacing(-0.05f);
        return customTab;


    }

    public void changBg(View view) {
        AppCompatButton acButton = findViewById(R.id.favorite_now_btn);

        if (buttonActive) {
            buttonActive = false;
            acButton.setText("Bỏ Thích");
            acButton.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.heartcheck), null, null);
        } else {
            buttonActive = true;
            acButton.setText("Yêu Thích");
            acButton.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(R.drawable.heartplus), null, null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

}