package com.myproject.novel.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.myproject.novel.CallbackMainActivity;
import com.myproject.novel.R;
import com.myproject.novel.model.SwipeModel;
import com.myproject.novel.ui.novel.NovelActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeAdapter extends PagerAdapter {

    private CallbackMainActivity mCallback;
    private Context context;

    List<SwipeModel> swipeModels;

    public HomeAdapter(Context context, List<SwipeModel> swipeModels,CallbackMainActivity mCallback) {
        this.context = context;
        this.swipeModels = swipeModels;
        this.mCallback = mCallback;
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.swipe_item, container, false);
        ImageView imageView = view.findViewById(R.id.swipe_item_image);
        SwipeModel swipeModel = swipeModels.get(position);

        if (swipeModel != null) {
            Glide.with(context).load(swipeModel.url).transition(
                    DrawableTransitionOptions.withCrossFade()).into(imageView);
        }
        imageView.setOnClickListener(v-> mCallback.startActivity(NovelActivity.class,null));
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(swipeModels != null)
        {
            return swipeModels.size();
        }
        return  0;
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView( (View)object);
    }
}
