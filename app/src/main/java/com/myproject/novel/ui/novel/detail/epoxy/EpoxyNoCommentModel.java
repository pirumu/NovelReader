package com.myproject.novel.ui.novel.detail.epoxy;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.myproject.novel.R;


@EpoxyModelClass
public abstract class EpoxyNoCommentModel extends EpoxyModelWithHolder<EpoxyNoCommentModel.EpoxyNoCommentHolder> {


    @Override
    protected int getDefaultLayout() {
        return R.layout.no_record;
    }

    @EpoxyAttribute
    public int novelId;
    @EpoxyAttribute
    public View.OnClickListener clickListener;

    public EpoxyNoCommentModel(int novelId, View.OnClickListener clickListener) {
        this.novelId = novelId;
        this.clickListener = clickListener;
    }

    @Override
    public void bind(@NonNull EpoxyNoCommentHolder holder) {
        super.bind(holder);
        holder.rootView.setOnClickListener(this.clickListener);
    }

    static class EpoxyNoCommentHolder extends EpoxyHolder {
        public LinearLayout rootView;
        public TextView title, action;

        @Override
        protected void bindView(@NonNull View itemView) {
//            title = itemView.findViewById(R.id.no_record_title);
            action = itemView.findViewById(R.id.no_record_click);
            rootView = itemView.findViewById(R.id.no_comment_item);
        }

    }
}
