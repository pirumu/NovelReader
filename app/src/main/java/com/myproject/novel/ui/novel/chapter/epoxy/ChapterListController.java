package com.myproject.novel.ui.novel.chapter.epoxy;

import com.airbnb.epoxy.TypedEpoxyController;
import com.myproject.novel.model.ChapterModel;

import java.util.List;

@SuppressWarnings("deprecation")
public class ChapterListController extends TypedEpoxyController<List<ChapterModel>> {

    private final ChapterListController.EpoxyAdapterCallbacks adapterCallbacks;

    public ChapterListController(ChapterListController.EpoxyAdapterCallbacks epoxyAdapterCallbacks) {
        this.adapterCallbacks = epoxyAdapterCallbacks;
    }

    @Override
    protected void buildModels(List<ChapterModel> data) {

        EpoxyChapterHeaderItemModel_ echi = new EpoxyChapterHeaderItemModel_(String.valueOf(data.size() + 1), v -> adapterCallbacks.sortOldClick(), v -> adapterCallbacks.sortNewClick());
        echi.id("chapter_list_header_id");
        echi.addTo(this);

        data.forEach(chapterModel -> {
            EpoxyChapterItemModel_ eci = new EpoxyChapterItemModel_(chapterModel.getChapterTitle(), chapterModel.getCreatedAt(), v -> adapterCallbacks.chapterTitleClick(chapterModel));
            eci.id(chapterModel.getChapterId());
            eci.addTo(this);
        });
    }


    public interface EpoxyAdapterCallbacks {
        void chapterTitleClick(ChapterModel model);

        void sortOldClick();

        void sortNewClick();
    }
}
