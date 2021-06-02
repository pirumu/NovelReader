package com.myproject.novel.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.GlideApp;
import com.myproject.novel.local.util.SharedPreferencesUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.BannerModel;
import com.myproject.novel.model.ListNovelHomeModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.home.epoxy.HomeController;
import com.myproject.novel.ui.main.CallbackMainActivity;
import com.myproject.novel.ui.novel.NovelActivity;
import com.snakydesign.livedataextensions.Lives;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment implements HomeController.EpoxyAdapterCallbacks {

    private CallbackMainActivity mCallback;

    private HomeViewModel mViewModel;
    private View rootView;
    private HomeController homeController;
    private Timer timer;
    private ViewPager viewpager;
    private AppCompatImageView bg;
    boolean isSetBg = false;
    private NestedScrollView nestedScrollView;
    List<ListNovelHomeModel> listNovelModels;
    private List<BannerModel> bannerModelList;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        bannerModelList = new ArrayList<>();
        listNovelModels = new ArrayList<>();
        CommonUtils.setFullScreenWithStatusBar(requireActivity(), true);
        bg = rootView.findViewById(R.id.default_bg);
        loadNestedScrollView();
        if ((Boolean) SharedPreferencesUtils.getParam(requireContext(), UC.IS_DARK_STATUS_BAR, false)) {
            isSetBg = true;
            mCallback.changeToolbarBackgroundColor(getString(R.string.defaut_color), getString(R.string.white_color), 0);
            CommonUtils.clearLightStatusBar(requireActivity());
            mCallback.changeTabLayoutColor(getString(R.string.popular_color), getString(R.string.tab_layout_color));
        } else {
            SharedPreferencesUtils.setParam(requireContext(), UC.IS_DARK_STATUS_BAR, false);
            mCallback.changeToolbarBackgroundColor(getString(R.string.white_color), getString(R.string.defaut_color), 0);
            CommonUtils.enableLightStatusBar(requireActivity());
            mCallback.changeTabLayoutColor(getString(R.string.white_color), getString(R.string.white_color));
        }
        setController(requireContext());

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewModelObserver();
    }

    private void setController(Context ctx) {
        if (ctx != null) {
            homeController = new HomeController(this);
            homeController.setDebugLoggingEnabled(true);
            RecyclerView homeRecyclerView = rootView.findViewById(R.id.recyclerView);
            int spanCount = 3;
            GridLayoutManager layoutManager = new GridLayoutManager(ctx, spanCount);
            homeController.setSpanCount(spanCount);
            layoutManager.setSpanSizeLookup(homeController.getSpanSizeLookup());
            homeRecyclerView.setLayoutManager(layoutManager);
            homeRecyclerView.setAdapter(homeController.getAdapter());
        }

    }

    private void setAdapter(Context ctx, List<BannerModel> bannerModelList) {
        if (ctx != null) {
            HomeAdapter homeAdapter = new HomeAdapter(ctx, bannerModelList, mCallback);
            viewpager = rootView.findViewById(R.id.swipe_view_pager);
            setBackgroundDefault(bannerModelList.get(0).getBannerUrl());
            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    setBackgroundDefault(bannerModelList.get(position).getBannerUrl());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            viewpager.setAdapter(homeAdapter);

            CircleIndicator indicator = rootView.findViewById(R.id.circle_indicator);
            indicator.setViewPager(viewpager);
            homeAdapter.registerDataSetObserver(indicator.getDataSetObserver());
            autoSlideImage();
        }
    }

    @Override
    public void novelTitleClick(NovelModel model) {
        HashMap<String, String> data = new HashMap<>();
        data.put(UC.NOVEL_ID, String.valueOf(model.getNovelId()));
        mCallback.startActivity(NovelActivity.class, data);
    }

    private void autoSlideImage() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        int currentItem = viewpager.getCurrentItem();
                        int total = bannerModelList.size() - 1;

                        if (currentItem < total) {
                            currentItem++;
                            viewpager.setCurrentItem(currentItem);
                        } else {
                            viewpager.setCurrentItem(0);
                        }

                    }
                });
            }
        }, 5000, 3000);
    }

    public void setBackgroundDefault(String url) {

        GlideApp.with(this).asBitmap()
                .load(url)
                .placeholder(CommonUtils.shimmerEffect())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        if (bitmap != null) {
                            Palette p = Palette.from(bitmap).generate();
                            Palette.Swatch vibrantDark = p.getDarkMutedSwatch();
                            Palette.Swatch vibrantLight = p.getLightMutedSwatch();

                            if (vibrantDark != null) {
                                bg.setImageBitmap(CommonUtils.makeGradient(bitmap, vibrantDark.getRgb(), Color.WHITE));
                            } else if (vibrantLight != null) {
                                bg.setImageBitmap(CommonUtils.makeGradient(bitmap, vibrantLight.getRgb(), Color.WHITE));
                            }
                        }
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isSetBg = false;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void loadNestedScrollView() {
        nestedScrollView = rootView.findViewById(R.id.home_nested_scroll);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY >= 6 && !isSetBg) {
                mCallback.changeToolbarBackgroundColor(getString(R.string.defaut_color), getString(R.string.white_color), 325);
                CommonUtils.clearLightStatusBar(requireActivity());
                SharedPreferencesUtils.setParam(requireContext(), UC.IS_DARK_STATUS_BAR, true);
                mCallback.changeTabLayoutColor(getString(R.string.popular_color), getString(R.string.tab_layout_color));
                isSetBg = true;
            } else if (scrollY < 6 && isSetBg) {
                SharedPreferencesUtils.setParam(requireContext(), UC.IS_DARK_STATUS_BAR, false);
                mCallback.changeToolbarBackgroundColor(getString(R.string.white_color), getString(R.string.defaut_color), 325);
                CommonUtils.enableLightStatusBar(requireActivity());
                mCallback.changeTabLayoutColor(getString(R.string.white_color), getString(R.string.white_color));
                isSetBg = false;
            }
        });
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

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    @Override
    public void onHiddenChanged(boolean hidd) {
        if (!hidd) {// When the fragment to appear from time to hide
            nestedScrollView.scrollTo(0, 0);
        }
    }


    public void onResume() {
        super.onResume();
        nestedScrollView.scrollTo(0, 0);
    }

    private void viewModelObserver() {
        mViewModel.bannerList.observe(getViewLifecycleOwner(), res -> {
            bannerModelList = res;
            setAdapter(requireContext(), bannerModelList);
        });
        Lives.zip(mViewModel.novelList1, mViewModel.novelList2, mViewModel.novelList3, (r1, r2, r3) -> {
            listNovelModels.add(new ListNovelHomeModel(UC.NOVEL_TYPE_1_ID, r1));
            listNovelModels.add(new ListNovelHomeModel(UC.NOVEL_TYPE_2_ID, r2));
            listNovelModels.add(new ListNovelHomeModel(UC.NOVEL_TYPE_3_ID, r3));
            return listNovelModels;
        }).observe(getViewLifecycleOwner(), res -> {
            homeController.setData(listNovelModels);
        });
    }
}

