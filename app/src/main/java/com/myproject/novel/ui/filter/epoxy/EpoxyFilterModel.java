package com.myproject.novel.ui.filter.epoxy;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.myproject.novel.R;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.model.TagModel;


@EpoxyModelClass
public abstract class EpoxyFilterModel  extends EpoxyModelWithHolder<EpoxyFilterModel.FilterHolder> {

    @EpoxyAttribute
    public TagModel tagModel;
    @EpoxyAttribute
    public View.OnClickListener clickListener;

    public EpoxyFilterModel(TagModel tagModel,View.OnClickListener clickListener) {
        this.tagModel = tagModel;
        this.clickListener = clickListener;
    }

    @Override
    protected int getDefaultLayout() {
        return  R.layout.filter_item;
    }

    @Override
    public void bind(@NonNull FilterHolder holder) {
        super.bind(holder);
        Glide.with(holder.tagImageView.getContext()).load(tagModel.url).transition(
                DrawableTransitionOptions.withCrossFade()).into(holder.tagImageView);
        holder.tagTitle.setText(tagModel.title);
        holder.tagTitle.setOnClickListener(clickListener);
        holder.tagDesc.setText(tagModel.desc);
        holder.tagImageView.setOnClickListener(clickListener);
        holder.tagDesc.setOnClickListener(clickListener);
    }

    static class FilterHolder extends EpoxyHolder {

        public TextView tagTitle,tagDesc;
        public ImageView tagImageView;

        @Override
        protected void bindView(@NonNull View itemView) {
            tagTitle = itemView.findViewById(R.id.tag_item_title);
            tagDesc = itemView.findViewById(R.id.tag_item_desc);
            tagImageView = itemView.findViewById(R.id.tag_item_image);
        }
    }

}

