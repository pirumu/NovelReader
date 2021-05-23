package com.myproject.novel.ui.home.epoxy;

import android.os.Handler;
import android.view.Gravity;
import android.view.View;

import com.airbnb.epoxy.BoundViewHolders;
import com.airbnb.epoxy.Carousel;
import com.airbnb.epoxy.CarouselModel_;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyViewHolder;
import com.airbnb.epoxy.OnModelBoundListener;
import com.airbnb.epoxy.TypedEpoxyController;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.myproject.novel.local.util.CarouselIndicatorModel;
import com.myproject.novel.local.util.CustomSnappingCarousel;
import com.myproject.novel.local.util.CustomSnappingCarouselModel_;
import com.myproject.novel.local.util.ICallback;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.model.SwipeModel;
import com.myproject.novel.ui.home.epoxy.EpoxyNovelModel;

import java.util.ArrayList;
import java.util.List;

import static com.airbnb.epoxy.Carousel.setDefaultGlobalSnapHelperFactory;

public class HomeController extends TypedEpoxyController<List<NovelModel>> {

    private final EpoxyAdapterCallbacks adapterCallbacks;

    public HomeController(EpoxyAdapterCallbacks epoxyAdapterCallbacks) {
        this.adapterCallbacks = epoxyAdapterCallbacks;
    }

    @Override
    protected void buildModels(List<NovelModel> data) {


        List<EpoxySwipeItemModel> swipeModels = new ArrayList<>();


        data.forEach( novelModel -> {
            EpoxySwipeItemModel_ epoxySwipeItemModel_ = new EpoxySwipeItemModel_(new SwipeModel(novelModel.novelId,novelModel.novelTitle,novelModel.novelDescription,novelModel.novelUrl),v -> adapterCallbacks.novelTitleClick(novelModel));
//             model = new EpoxyNovelModel_(novelModel, v -> adapterCallbacks.novelTitleClick(novelModel));
            epoxySwipeItemModel_.id((long) novelModel.novelId);
//            model.addTo(this);
            swipeModels.add(epoxySwipeItemModel_);
//            model.addTo(this);
        });

        List<EpoxyNovelModel> epoxyNovelModels = new ArrayList<>();
//
//
        EpoxyHeaderItemModel_ epoxyHeaderItemModel_ =  new EpoxyHeaderItemModel_();
        epoxyHeaderItemModel_.id("tdc");
        epoxyHeaderItemModel_.setTypeName("Truyện Đề Cử");
        epoxyHeaderItemModel_.spanSizeOverride(new EpoxyModel.SpanSizeOverrideCallback() {
            @Override
            public int getSpanSize(int totalSpanCount, int position, int itemCount) {
                return totalSpanCount;
            }
        });
        epoxyHeaderItemModel_.addTo(this);
        data.forEach( novelModel -> {
            EpoxyNovelModel_ model = new EpoxyNovelModel_(novelModel, v -> adapterCallbacks.novelTitleClick(novelModel));
            model.id((long) novelModel.novelId);
//            model.addTo(this);
            epoxyNovelModels.add(model);
            model.addTo(this);
        });

        setDefaultGlobalSnapHelperFactory(null);
//        new CarouselModel_()
//                .id("carousel")
//                .models(epoxyNovelModels)
//                .padding(Carousel.Padding.dp(12, 0, 12, 0, 10))
//                .addTo(this);
////
//
        EpoxyHeaderItemModel_ epoxyHeaderItemModel1_ =  new EpoxyHeaderItemModel_();
        epoxyHeaderItemModel1_.id("th");
        epoxyHeaderItemModel1_.setTypeName("Truyện Hot");
        epoxyHeaderItemModel1_.addTo(this);
        new CarouselModel_()
                .id("carousel")
                .models(epoxyNovelModels)
                .padding(Carousel.Padding.dp(0, 0, 10, 0, 0))
                .addTo(this);

//        epoxyNovelModels.forEach( epoxyNovelModel -> epoxyNovelModel.addTo(this));


        EpoxyHeaderItemModel_ epoxyHeaderItemModel2_ =  new EpoxyHeaderItemModel_();
        epoxyHeaderItemModel2_.id("tnt");
        epoxyHeaderItemModel2_.setTypeName("Truyện Kinh Điển");
        epoxyHeaderItemModel2_.spanSizeOverride(new EpoxyModel.SpanSizeOverrideCallback() {
            @Override
            public int getSpanSize(int totalSpanCount, int position, int itemCount) {
                return totalSpanCount;
            }
        }).addTo(this);
        data.forEach( novelModel -> {
            EpoxyNovelFullModel_ model = new EpoxyNovelFullModel_(novelModel, v -> adapterCallbacks.novelTitleClick(novelModel));
            model.id((long) novelModel.novelId);
            model.addTo(this);
        });
    }

    public interface EpoxyAdapterCallbacks {
        void novelTitleClick(NovelModel model);
    }
}