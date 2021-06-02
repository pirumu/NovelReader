package com.myproject.novel.ui.search.epoxy;

import com.airbnb.epoxy.TypedEpoxyController;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.home.epoxy.EpoxyNovelFullModel_;

import java.util.List;
import java.util.UUID;

public class SearchController extends TypedEpoxyController<List<NovelModel>> {

    private final SearchController.EpoxyAdapterCallbacks adapterCallbacks;

    public SearchController(SearchController.EpoxyAdapterCallbacks epoxyAdapterCallbacks) {
        this.adapterCallbacks = epoxyAdapterCallbacks;
    }

    @Override
    protected void buildModels(List<NovelModel> data) {
        setType3(data);
    }

    private void setType3(List<NovelModel> data) {

        data.forEach(novelModel -> {
            EpoxyNovelFullModel_ model = new EpoxyNovelFullModel_(novelModel, v -> adapterCallbacks.novelTitleClick(novelModel));
            model.id(UUID.randomUUID().toString());
            model.addTo(this);
        });
    }

    public interface EpoxyAdapterCallbacks {
        void novelTitleClick(NovelModel model);
    }
}
