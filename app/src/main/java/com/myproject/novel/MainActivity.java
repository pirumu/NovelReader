package com.myproject.novel;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.ui.favorite.FavoriteFragment;
import com.myproject.novel.ui.filter.FilterFragment;
import com.myproject.novel.ui.home.HomeFragment;
import com.myproject.novel.ui.user.UserFragment;

import java.util.HashMap;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements CallbackMainActivity {

    private String genderBoyTag = "boy";
    private String genderGirlTag = "girl";
    private  TabLayout tabLayout;
    private Toolbar myToolbar;
    private int postionActiveTab = 0;

    private HomeFragment homeFragment ;
    private FilterFragment filterFragment ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadChipNavigationBar();
        loadToolBar();
        loadTab();
        settingGenderChoice();
    }


    public void  loadToolBar() {
         myToolbar = (Toolbar) findViewById(R.id.app_toolbar);
         myToolbar.setTitle("");
         setSupportActionBar(myToolbar);
    }
    private void loadChipNavigationBar() {

        ChipNavigationBar chipNavigationBar = findViewById(R.id.bottom_navigation_view);

        loadDefaultView(chipNavigationBar);



        chipNavigationBar.setOnItemSelectedListener(id -> {

            tabLayout.setVisibility(View.VISIBLE);

            if (id == R.id.home_bottom_menu) {
                loadFragment(new HomeFragment());
            } else if (id == R.id.favorite_bottom_menu) {
                loadFragment(new FavoriteFragment());
            } else if (id == R.id.filter_bottom_menu) {
                loadFragment(new FilterFragment());
            }
            else {
                tabLayout.setVisibility(View.GONE);
                loadFragment(new UserFragment());
            }

        });


    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container_view, fragment);
        transaction.commit();
    }

    private void loadDefaultView(ChipNavigationBar chipNavigationBar) {
        homeFragment = new HomeFragment();
        filterFragment = new FilterFragment();
        loadFragment(homeFragment);
        //set default
        chipNavigationBar.setItemSelected(R.id.home_bottom_menu, true);
    }


    private void loadTab() {
        tabLayout  = findViewById(R.id.app_tab);
        tabLayout.setTabRippleColor(null);
        tabLayout.removeAllTabs();
        TextView suggest = (TextView) getLayoutInflater().inflate(R.layout.custom_tab, null);
        suggest.setText(R.string.suggest_txt);
        suggest.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        suggest.setTextColor(getResources().getColor(R.color.tab_color));
        suggest.setLetterSpacing(-0.05f);
        tabLayout.addTab(tabLayout.newTab().setCustomView(suggest));
        TextView explore = (TextView) getLayoutInflater().inflate(R.layout.custom_tab, null);
        explore.setText(R.string.explore_txt);
        explore.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        explore.setTextColor(getResources().getColor(R.color.tab_color));
        explore.setLetterSpacing(-0.05f);
        tabLayout.addTab(tabLayout.newTab().setCustomView(explore));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                postionActiveTab = tab.getPosition();
                TextView tabActive = (TextView) tab.getCustomView();
                assert tabActive != null;
                tabActive.setTextSize(15);

                if(((TextView) tab.getCustomView()).getText() == getString(R.string.suggest_txt)) {
                    loadFragment(homeFragment);
                } else {
//                    loadFragment(new SearchFragment());
                    loadFragment(filterFragment);
//                    startActivity(NovelActivity.class);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tabActive = (TextView) tab.getCustomView();
                assert tabActive != null;
                tabActive.setTextSize(13);
                tabActive.setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }


    private void settingGenderChoice() {
        AppCompatImageView genderChoice = findViewById(R.id.gender_choice);
        genderChoice.setTag(genderBoyTag);
        genderChoice.setClickable(true);
        genderChoice.setOnClickListener(v -> {
            if(genderChoice.getTag() == genderBoyTag)
            {
                genderChoice.setImageResource(R.drawable.girl_ic);
                genderChoice.setTag(genderGirlTag);
            } else {
                genderChoice.setImageResource(R.drawable.boy_ic);
                genderChoice.setTag(genderBoyTag);
            }
        });
        Drawable mIcon= ContextCompat.getDrawable(this ,R.drawable.search_white);
        mIcon.setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.MULTIPLY);
        AppCompatImageView mImageView = findViewById(R.id.find_now);
        mImageView.setImageDrawable(mIcon);
    }

    @Override
    public void changeTabLayoutColor(String indicatorColor,String textColor) {
        Objects.requireNonNull(tabLayout.getTabAt(postionActiveTab)).select();
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            TextView item = (TextView)tab.getCustomView();
            assert item != null;
            item.setTextColor(Color.parseColor(textColor));
        }

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor(indicatorColor));

    }

    @Override
    public void changeToolbarBackgroundColor(String startColor, String endColor,int duration) {
        //"#00000000" start
        //"#FFFFFF" end
        // 325 duration
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            float fractionAnim = (float) valueAnimator1.getAnimatedValue();
            myToolbar.setBackgroundColor(ColorUtils.blendARGB(Color.parseColor(startColor)
                    , Color.parseColor(endColor)
                    , fractionAnim));

        });
        valueAnimator.start();
    }

    public void startActivity(Class<?> className, HashMap<String,String> data){
        Intent intent = new Intent(this, className);
        intent.putExtra("ClassName",className.toString());
        startActivity(intent);
    }

    public void destroyTabLayout() {
        tabLayout.removeAllViews();
    }

}



