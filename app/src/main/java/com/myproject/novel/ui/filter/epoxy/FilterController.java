package com.myproject.novel.ui.filter.epoxy;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.myproject.novel.model.TagModel;
import com.myproject.novel.ui.home.epoxy.EpoxyHeaderItemModel_;

import java.util.List;

public class FilterController  extends TypedEpoxyController<List<TagModel>> {

    private final FilterController.EpoxyAdapterCallbacks adapterCallbacks;

    public FilterController(FilterController.EpoxyAdapterCallbacks epoxyAdapterCallbacks) {
        this.adapterCallbacks = epoxyAdapterCallbacks;
    }

    @Override
    protected void buildModels(List<TagModel> data) {

        EpoxyHeaderItemModel_ epoxyHeaderItemModel_ =  new EpoxyHeaderItemModel_();
        epoxyHeaderItemModel_.id("Tag");
        epoxyHeaderItemModel_.setTypeName("Danh Mục Truyện");
        epoxyHeaderItemModel_.spanSizeOverride(new EpoxyModel.SpanSizeOverrideCallback() {
            @Override
            public int getSpanSize(int totalSpanCount, int position, int itemCount) {
                return totalSpanCount;
            }
        });
        epoxyHeaderItemModel_.addTo(this);
        data.forEach( tagModel -> {
            EpoxyFilterModel_ md = new EpoxyFilterModel_(tagModel, v -> adapterCallbacks.tagClick(tagModel));
            md.id((long) tagModel.Id);
            md.addTo(this);
        });
    }

    public interface EpoxyAdapterCallbacks {
        void tagClick(TagModel model);
    }
}
