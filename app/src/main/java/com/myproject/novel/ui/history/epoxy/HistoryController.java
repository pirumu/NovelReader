package com.myproject.novel.ui.history.epoxy;

import com.airbnb.epoxy.TypedEpoxyController;
import com.myproject.novel.model.ListNovelModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.favorite.epoxy.EpoxyNoRecordModel_;
import com.myproject.novel.ui.home.epoxy.EpoxyNovelFullModel_;

import java.util.UUID;

public class HistoryController extends TypedEpoxyController<ListNovelModel> {
    private final EpoxyAdapterCallbacks mCallback;

    public HistoryController(EpoxyAdapterCallbacks cb) {
        this.mCallback = cb;
    }

    @Override
    protected void buildModels(ListNovelModel data) {
        if (data == null || data.getData().isEmpty()) {
            EpoxyNoRecordModel_ noRecord = new EpoxyNoRecordModel_();
            noRecord.id(UUID.randomUUID().toString());
            noRecord.addTo(this);
        } else {
            data.getData().forEach(novelModel -> {
                EpoxyNovelFullModel_ model = new EpoxyNovelFullModel_(novelModel, v -> mCallback.novelTitleClick(novelModel));
                model.id(UUID.randomUUID().toString());
                model.addTo(this);
            });
        }
    }


    public interface EpoxyAdapterCallbacks {
        void novelTitleClick(NovelModel model);
    }

}
