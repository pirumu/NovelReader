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
public abstract class EpoxyAllCommentModel extends EpoxyModelWithHolder<EpoxyAllCommentModel.EpoxyAllCommentHolder> {

    @EpoxyAttribute
    public Integer totalComment;
    @EpoxyAttribute
    public View.OnClickListener clickListener;


    public EpoxyAllCommentModel(Integer totalComment, View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        this.totalComment = totalComment;
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.all_comment;
    }

    @Override
    public void bind(@NonNull EpoxyAllCommentHolder holder) {
        super.bind(holder);
        holder.totalComment.setText(String.format("Tất cả bình luận(%s)", totalComment));
        holder.linearLayout.setOnClickListener(clickListener);

    }

    static class EpoxyAllCommentHolder extends EpoxyHolder {

        public TextView totalComment;
        public LinearLayout linearLayout;


        @Override
        protected void bindView(@NonNull View itemView) {
            totalComment = itemView.findViewById(R.id.total_comment);
            linearLayout = itemView.findViewById(R.id.all_comment_root_layout);


        }

    }
}
