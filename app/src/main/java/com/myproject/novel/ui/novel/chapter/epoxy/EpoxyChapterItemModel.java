package com.myproject.novel.ui.novel.chapter.epoxy;

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
public abstract class EpoxyChapterItemModel extends EpoxyModelWithHolder<EpoxyChapterItemModel.HeaderItemHolder> {

    private final String defaultText = ".";
    @EpoxyAttribute
    String chapterName;
    @EpoxyAttribute
    String createAt;
    @EpoxyAttribute
    public View.OnClickListener clickListener;

    public EpoxyChapterItemModel(String chapterName, String createAt, View.OnClickListener clickListener) {
        this.chapterName = String.format("%s %s", chapterName, defaultText);
        this.createAt = createAt;
        this.clickListener = clickListener;
    }


    @Override
    protected int getDefaultLayout() {
        return R.layout.chapter_item;
    }

    @Override
    public void bind(@NonNull HeaderItemHolder holder) {
        super.bind(holder);
        holder.chapterName.setText(chapterName);
        holder.createdAt.setText(createAt);
        holder.wrapChapter.setOnClickListener(clickListener);
    }

    static class HeaderItemHolder extends EpoxyHolder {

        public TextView chapterName, createdAt;
        public LinearLayout wrapChapter;

        @Override
        protected void bindView(@NonNull View itemView) {
            chapterName = itemView.findViewById(R.id.chapter_name);
            createdAt = itemView.findViewById(R.id.created_at);
            wrapChapter = itemView.findViewById(R.id.root_layout);
        }


    }

}
