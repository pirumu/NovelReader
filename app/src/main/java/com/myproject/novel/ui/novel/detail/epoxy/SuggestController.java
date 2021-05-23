package com.myproject.novel.ui.novel.detail.epoxy;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.home.epoxy.EpoxyHeaderItemModel_;
import com.myproject.novel.ui.home.epoxy.EpoxyNovelModel_;

import java.util.List;

public class SuggestController extends TypedEpoxyController<List<NovelModel>> {

    private final SuggestController.EpoxyAdapterCallbacks adapterCallbacks;

    public SuggestController(SuggestController.EpoxyAdapterCallbacks epoxyAdapterCallbacks) {
        this.adapterCallbacks = epoxyAdapterCallbacks;
    }

    @Override
    protected void buildModels(List<NovelModel> data) {


        EpoxyHeaderItemModel_ epoxyHeaderItemModel_ =  new EpoxyHeaderItemModel_();
        epoxyHeaderItemModel_.id("tdc");
        epoxyHeaderItemModel_.setTypeName("Đề Xuất Liên Quan");
        epoxyHeaderItemModel_.spanSizeOverride((totalSpanCount, position, itemCount) -> totalSpanCount);
        epoxyHeaderItemModel_.addTo(this);
        data.forEach( novelModel -> {
            EpoxyNovelModel_ model = new EpoxyNovelModel_(novelModel, v -> adapterCallbacks.novelTitleClick(novelModel));
            model.id((long) novelModel.novelId);
            model.addTo(this);
        });


    }


    public interface EpoxyAdapterCallbacks {
        void novelTitleClick(NovelModel model);
    }


}
