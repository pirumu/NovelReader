package com.myproject.novel.ui.novel.chapter.epoxy;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.myproject.novel.R;

@EpoxyModelClass
public abstract class EpoxyChapterHeaderItemModel extends EpoxyModelWithHolder<EpoxyChapterHeaderItemModel.HeaderItemHolder> {

    private final String defaultText = "Cập nhật đến chap";
    @EpoxyAttribute
    String currentChapter;
    @EpoxyAttribute
    public View.OnClickListener clickListener;

    public EpoxyChapterHeaderItemModel( String currentChapter,View.OnClickListener clickListener) {
        this.currentChapter= String.format("%s %s",defaultText,currentChapter);
        this.clickListener = clickListener;
    }


    @Override
    protected int getDefaultLayout() {
        return  R.layout.chapter_header_item;
    }

    @Override
    public void bind(@NonNull HeaderItemHolder holder) {
        super.bind(holder);
        holder.updateCurrent.setText(currentChapter);
        holder.sortNew.setOnClickListener(clickListener);
        holder.sortOld.setOnClickListener(clickListener);
    }

    static class HeaderItemHolder extends EpoxyHolder {

        public TextView updateCurrent,sortOld,sortNew;

        @Override
        protected void bindView(@NonNull View itemView) {
            updateCurrent = itemView.findViewById(R.id.update_new);
            sortOld = itemView.findViewById(R.id.sort_old);
            sortNew = itemView.findViewById(R.id.sort_new);
        }
    }

}
