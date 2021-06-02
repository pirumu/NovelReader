package com.myproject.novel.ui.home.epoxy;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.myproject.novel.R;
import com.myproject.novel.model.BannerModel;

@EpoxyModelClass
public abstract class EpoxySwipeItemModel extends EpoxyModelWithHolder<EpoxySwipeItemModel.SwipeItemHolder> {


    @EpoxyAttribute
    BannerModel bannerModel;

    public EpoxySwipeItemModel(BannerModel bannerModel) {

        this.bannerModel = bannerModel;

    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.swipe_item;
    }


    @Override
    public void bind(@NonNull SwipeItemHolder holder) {
        super.bind(holder);

        Glide.with(holder.swipeItemImage.getContext())
                .load(bannerModel.getBannerUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.swipeItemImage);

    }

    static class SwipeItemHolder extends EpoxyHolder {

        public ImageView swipeItemImage;

        @Override
        protected void bindView(@NonNull View itemView) {

            swipeItemImage = itemView.findViewById(R.id.swipe_item_image);

        }
    }
}
