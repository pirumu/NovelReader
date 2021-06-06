package com.myproject.novel.ui.favorite.epoxy;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.myproject.novel.R;
import com.myproject.novel.local.util.UC;

@EpoxyModelClass
public abstract class EpoxyNoRecordModel extends EpoxyModelWithHolder<EpoxyNoRecordModel.EpoxyNoRecordHolder> {

    @Override
    protected int getDefaultLayout() {
        return R.layout.no_record;
    }

    public EpoxyNoRecordModel() {
    }

    @Override
    public void bind(@NonNull EpoxyNoRecordModel.EpoxyNoRecordHolder holder) {
        super.bind(holder);
        holder.action.setVisibility(View.GONE);
        holder.title.setText(UC.NO_NOVEL);
        holder.rootView.setPadding(0, 120, 0, 0);
    }

    static class EpoxyNoRecordHolder extends EpoxyHolder {
        public LinearLayout rootView;
        public TextView title, action;

        @Override
        protected void bindView(@NonNull View itemView) {
            title = itemView.findViewById(R.id.no_record_title);
            action = itemView.findViewById(R.id.no_record_click);
            rootView = itemView.findViewById(R.id.no_comment_item);
        }

    }
}
