package com.myproject.novel.ui.home;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.myproject.novel.CallbackMainActivity;
import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.GlideApp;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.model.SwipeModel;
import com.myproject.novel.ui.home.epoxy.HomeController;
import com.myproject.novel.ui.novel.NovelActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
    boolean isLightStatusBar = false;
    private NestedScrollView nestedScrollView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        bg = rootView.findViewById(R.id.default_bg);
        nestedScrollView = rootView.findViewById(R.id.home_nested_scroll);
        nestedScrollView.fullScroll(NestedScrollView.FOCUS_UP);
        loadNestedScrollView();
        setController(requireContext());
        setAdapter(requireContext(), dumpData2());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CommonUtils.setFullScreenWithStatusBar(requireActivity(), false);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeController.setData(dumpData());

        mCallback.changeToolbarBackgroundColor("#FFFFFF", "#00000000", 325);
//        if(isLightStatusBar) {
//            isLightStatusBar = false;
//            CommonUtils.clearLightStatusBar(requireActivity());
//
//        } else {
//            isLightStatusBar = true;
//            CommonUtils.enableLightStatusBar(requireActivity());
//        }
        mCallback.changeTabLayoutColor("#FFFFFF", "#FFFFFF");


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

    private void setAdapter(Context ctx, List<SwipeModel> swipeModels) {
        if (ctx != null) {
            HomeAdapter homeAdapter = new HomeAdapter(ctx, swipeModels,mCallback);
            viewpager = (ViewPager) rootView.findViewById(R.id.swipe_view_pager);
            setBackgroundDefault(dumpData2().get(0).url);
            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    setBackgroundDefault(dumpData2().get(position).url);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            viewpager.setAdapter(homeAdapter);

            CircleIndicator indicator = (CircleIndicator) rootView.findViewById(R.id.circle_indicator);
            indicator.setViewPager(viewpager);
            homeAdapter.registerDataSetObserver(indicator.getDataSetObserver());
            autoSlideImage();
        }
    }

    @Override
    public void novelTitleClick(NovelModel model) {
        mCallback.startActivity(NovelActivity.class,null);
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
                        int total = dumpData2().size() - 1;

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

    private List<NovelModel> dumpData() {

        List<NovelModel> novelModelList = new ArrayList<>();

        novelModelList.add(new NovelModel(1,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Đại Mộng Đương Giác",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/4784170c47.jpg-posterend4"
        ));
        novelModelList.add(new NovelModel(2,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Duẫn",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/3057297ff5.jpg-posterend4"

        ));
        novelModelList.add(new NovelModel(3,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Ôn Thụy An",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/79804526d6.jpg-posterend4"
        ));
        novelModelList.add(new NovelModel(4,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Đại Mộng Đương Giác",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/366260a153.jpg-posterend4"
        ));
        novelModelList.add(new NovelModel(5,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Duẫn",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/7604317d88.jpg"

        ));
        novelModelList.add(new NovelModel(6,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Ôn Thụy An",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/5417029f18.jpg-posterend4"
        ));
        return novelModelList;

    }

    private List<SwipeModel> dumpData2() {

        List<SwipeModel> novelModelList = new ArrayList<>();
        novelModelList.add(new SwipeModel(1,
                "LIỀU MẠNG CÔNG LƯỢC VAI ÁC",
                "Đại Mộng Đương Giác",
                "http://cn.e.pic.mangatoon.mobi/homepage-banners/521-42fc.webp"
        ));
        novelModelList.add(new SwipeModel(2,
                "BẦU BẠN CÙNG MẶT TRĂNG",
                "Duẫn",
                "http://cn.e.pic.mangatoon.mobi/homepage-banners/522-8b84.webp"

        ));
        novelModelList.add(new SwipeModel(3,
                "NHẤT NỘ BẠT KIẾM",
                "Ôn Thụy An",
                "http://cn.e.pic.mangatoon.mobi/homepage-banners/803-cdc2.webp"
        ));
        novelModelList.add(new SwipeModel(4,
                "LIỀU MẠNG CÔNG LƯỢC VAI ÁC",
                "Đại Mộng Đương Giác",
                "http://cn.e.pic.mangatoon.mobi/homepage-banners/521-42fc.webp"
        ));
        novelModelList.add(new SwipeModel(5,
                "BẦU BẠN CÙNG MẶT TRĂNG",
                "Duẫn",
                "http://cn.e.pic.mangatoon.mobi/homepage-banners/522-8b84.webp"

        ));
        novelModelList.add(new SwipeModel(6,
                "NHẤT NỘ BẠT KIẾM",
                "Ôn Thụy An",
                "http://cn.e.pic.mangatoon.mobi/homepage-banners/803-cdc2.webp"
        ));
        return novelModelList;

    }

    public void setBackgroundDefault(String url) {

        GlideApp.with(this).asBitmap().load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
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

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY >= 6 && !isSetBg) {
                mCallback.changeToolbarBackgroundColor("#00000000", "#FFFFFF", 325);
                CommonUtils.clearLightStatusBar(requireActivity());
                mCallback.changeTabLayoutColor("#4896f0", "#323131");
                isSetBg = true;
            } else if (scrollY < 6 && isSetBg) {
                mCallback.changeToolbarBackgroundColor("#FFFFFF", "#00000000", 325);
                CommonUtils.enableLightStatusBar(requireActivity());
                mCallback.changeTabLayoutColor("#FFFFFF", "#FFFFFF");
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
}