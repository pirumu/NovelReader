package com.myproject.novel.ui.novel.chapter.epoxy;

import com.airbnb.epoxy.TypedEpoxyController;
import com.myproject.novel.model.ChapterModel;

import java.util.List;

public class ChapterListController  extends TypedEpoxyController<List<ChapterModel>> {

    private final ChapterListController.EpoxyAdapterCallbacks adapterCallbacks;

    public ChapterListController(ChapterListController.EpoxyAdapterCallbacks epoxyAdapterCallbacks) {
        this.adapterCallbacks = epoxyAdapterCallbacks;
    }

    @Override
    protected void buildModels(List<ChapterModel> data) {

        EpoxyChapterHeaderItemModel_ echi = new EpoxyChapterHeaderItemModel_("120",v->adapterCallbacks.sortClick(data.get(0)));
        echi.id("chapter_list_header_id");
        echi.addTo(this);

        data.forEach( chapterModel -> {
            EpoxyChapterItemModel_  eci = new EpoxyChapterItemModel_(chapterModel.chapterTitle,"28/02/2021",v->adapterCallbacks.chapterTitleClick(chapterModel));
            eci.id(chapterModel.chapterId);
            eci.addTo(this);
        });
    }


    public interface EpoxyAdapterCallbacks {
        void chapterTitleClick(ChapterModel model);
        void sortClick(ChapterModel model);
    }
}
