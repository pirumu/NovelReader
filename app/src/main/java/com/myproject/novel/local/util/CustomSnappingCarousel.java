package com.myproject.novel.local.util;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.epoxy.Carousel;
import com.airbnb.epoxy.ModelView;

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
public class CustomSnappingCarousel extends Carousel {
    public CustomSnappingCarousel(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public CustomSnappingCarousel(Context context){
        super(context);
    }
    private int selectedPosition = RecyclerView.NO_POSITION;

    public void setSnapHelperCallback(ICallback callback) {

        CustomLinearSnapHelper snapHelper = new CustomLinearSnapHelper(callback);
        //workaround - do not remove
        //https://stackoverflow.com/questions/44043501/an-instance-of-onflinglistener-already-set-in-recyclerview/52850198
        this.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(this);
    }

    class CustomLinearSnapHelper extends LinearSnapHelper {

        public CustomLinearSnapHelper(ICallback callback) {
            this.callback = callback;
        }
        private final ICallback callback;
        @Override
        public View findSnapView(LayoutManager layoutManager) {
            View root = super.findSnapView(layoutManager);
            if (root != null) {
                int newPosition = layoutManager.getPosition(root);
                if (newPosition != selectedPosition) {
                    callback.invoke(newPosition);
                    selectedPosition = newPosition;
                }
            }
            return root;

        }


    }
}


