package com.myproject.novel.ui.home.epoxy;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.myproject.novel.R;

@EpoxyModelClass
public abstract class EpoxyHeaderItemModel extends EpoxyModelWithHolder<EpoxyHeaderItemModel.HeaderItemHolder> {

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @EpoxyAttribute
    String typeName;

    @Override
    protected int getDefaultLayout() {
        return R.layout.home_header_title;
    }

    @Override
    public void bind(@NonNull HeaderItemHolder holder) {
        super.bind(holder);
        holder.novelType.setText(typeName);
    }

    static class HeaderItemHolder extends EpoxyHolder {

        public TextView novelType;

        @Override
        protected void bindView(@NonNull View itemView) {
            novelType = itemView.findViewById(R.id.type_name);
        }
    }

}
