package com.myproject.novel.ui.tag.epoxy;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.GlideApp;
import com.myproject.novel.model.TagModel;


@EpoxyModelClass
public abstract class EpoxyTagModel extends EpoxyModelWithHolder<EpoxyTagModel.FilterHolder> {

    @EpoxyAttribute
    public TagModel tagModel;
    @EpoxyAttribute
    public View.OnClickListener clickListener;

    public EpoxyTagModel(TagModel tagModel, View.OnClickListener clickListener) {
        this.tagModel = tagModel;
        this.clickListener = clickListener;
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.tag_item;
    }

    @Override
    public void bind(@NonNull FilterHolder holder) {
        super.bind(holder);
        GlideApp.with(holder.tagImageView.getContext())
                .load(tagModel.getTagPhotoUrl())
                .placeholder(CommonUtils.shimmerEffect())
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.tagImageView);
        holder.tagTitle.setText(tagModel.getTagName());
        holder.tagTitle.setOnClickListener(clickListener);
        String totalNovel = tagModel.getNovelsCount() + " Truyện";
        holder.tagDesc.setText(totalNovel);
        holder.tagImageView.setOnClickListener(clickListener);
        holder.tagDesc.setOnClickListener(clickListener);
    }

    static class FilterHolder extends EpoxyHolder {

        public TextView tagTitle, tagDesc;
        public ImageView tagImageView;

        @Override
        protected void bindView(@NonNull View itemView) {
            tagTitle = itemView.findViewById(R.id.tag_item_title);
            tagDesc = itemView.findViewById(R.id.tag_item_desc);
            tagImageView = itemView.findViewById(R.id.tag_item_image);
        }
    }

}

