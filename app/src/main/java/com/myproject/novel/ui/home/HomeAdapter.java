package com.myproject.novel.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.model.BannerModel;
import com.myproject.novel.ui.main.CallbackMainActivity;
import com.myproject.novel.ui.novel.NovelActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import static com.myproject.novel.local.util.UC.NOVEL_ID;

public class HomeAdapter extends PagerAdapter {

    private final CallbackMainActivity mCallback;
    private final Context context;

    List<BannerModel> bannerModelList;

    public HomeAdapter(Context context, List<BannerModel> bannerModelList, CallbackMainActivity mCallback) {
        this.context = context;
        this.bannerModelList = bannerModelList;
        this.mCallback = mCallback;
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.swipe_item, container, false);
        ImageView imageView = view.findViewById(R.id.swipe_item_image);
        BannerModel bannerModel = bannerModelList.get(position);

        if (bannerModel != null) {
            Glide.with(context).load(bannerModel.getBannerUrl())
                    .placeholder(CommonUtils.shimmerEffect())
                    .onlyRetrieveFromCache(true)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

            HashMap<String, String> data = new HashMap<>();
            data.put(NOVEL_ID, String.valueOf(bannerModel.getBannerId()));
            imageView.setOnClickListener(v -> mCallback.startActivity(NovelActivity.class, data));
        }

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (bannerModelList != null) {
            return bannerModelList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((View) object);
    }


}
