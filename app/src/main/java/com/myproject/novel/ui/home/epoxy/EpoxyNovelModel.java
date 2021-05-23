package com.myproject.novel.ui.home.epoxy;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.myproject.novel.R;
import com.myproject.novel.model.NovelModel;

@EpoxyModelClass
public abstract class EpoxyNovelModel extends EpoxyModelWithHolder<EpoxyNovelModel.EpoxyNovelHolder> {
    @Override
    protected int getDefaultLayout() {
        return R.layout.novel_common_item;
    }

    @EpoxyAttribute
    public NovelModel novelModel;
    @EpoxyAttribute
    public View.OnClickListener clickListener;


    public EpoxyNovelModel(NovelModel novelModel, View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        this.novelModel = novelModel;
    }


    @Override
    public void bind(@NonNull EpoxyNovelHolder holder) {
        super.bind(holder);
        Glide.with(holder.novelImageView.getContext()).load(novelModel.novelUrl).transition(
                DrawableTransitionOptions.withCrossFade()).into(holder.novelImageView);
        holder.novelTitle.setText(novelModel.novelTitle);
        holder.novelTitle.setOnClickListener(clickListener);
        holder.wrapImage.setOnClickListener(clickListener);
    }
    static class EpoxyNovelHolder extends EpoxyHolder {

        public AppCompatImageView novelImageView,wrapImage;
        public TextView novelTitle;
        @Override
        protected void bindView(@NonNull  View itemView) {
            novelImageView = (AppCompatImageView)itemView.findViewById(R.id.novel_background_image);
            novelTitle = (TextView)itemView.findViewById(R.id.novel_title);
            wrapImage = (AppCompatImageView)itemView.findViewById(R.id.wrap_image);
        }

    }


}