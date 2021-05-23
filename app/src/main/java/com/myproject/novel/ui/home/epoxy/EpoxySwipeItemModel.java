package com.myproject.novel.ui.home.epoxy;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.myproject.novel.R;
import com.myproject.novel.model.SwipeModel;

@EpoxyModelClass
public abstract class EpoxySwipeItemModel extends EpoxyModelWithHolder<EpoxySwipeItemModel.SwipeItemHolder> {


    @EpoxyAttribute
    SwipeModel swipeModel;

    @EpoxyAttribute
    public View.OnClickListener clickListener;

    public EpoxySwipeItemModel(SwipeModel swipeModel,View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        this.swipeModel = swipeModel;

    }
    @Override
    protected int getDefaultLayout() {
        return  R.layout.swipe_item;
    }


    @Override
    public void bind(@NonNull SwipeItemHolder holder) {
        super.bind(holder);

        Glide.with(holder.swipeItemImage.getContext()).load(swipeModel.url).transition(
                DrawableTransitionOptions.withCrossFade()).into(holder.swipeItemImage);
        holder.wrapImage.setOnClickListener(clickListener);
        holder.swipeItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"vvv",Toast.LENGTH_LONG).show();
            }
        });
    }

    static class SwipeItemHolder extends EpoxyHolder {

        public CardView wrapImage;
        public ImageView swipeItemImage;

        @Override
        protected void bindView(@NonNull View itemView) {

            swipeItemImage = (ImageView)itemView.findViewById(R.id.swipe_item_image);
            wrapImage = (CardView)itemView.findViewById(R.id.wrap_image);
        }
    }
}
