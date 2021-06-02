package com.myproject.novel.ui.home.epoxy;

import com.airbnb.epoxy.Carousel;
import com.airbnb.epoxy.CarouselModel_;
import com.airbnb.epoxy.TypedEpoxyController;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.ListNovelHomeModel;
import com.myproject.novel.model.ListNovelModel;
import com.myproject.novel.model.NovelModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.airbnb.epoxy.Carousel.setDefaultGlobalSnapHelperFactory;

public class HomeController extends TypedEpoxyController<List<ListNovelHomeModel>> {

    private final EpoxyAdapterCallbacks adapterCallbacks;

    public HomeController(EpoxyAdapterCallbacks epoxyAdapterCallbacks) {
        this.adapterCallbacks = epoxyAdapterCallbacks;
    }

    @Override
    protected void buildModels(List<ListNovelHomeModel> listNovelHomeModels) {
        listNovelHomeModels.forEach(listNovelHomeModel -> {
            switch (listNovelHomeModel.getType()) {
                case UC.NOVEL_TYPE_1_ID:
                    setType1(listNovelHomeModel.getListNovelModel());
                    setSpecialType(listNovelHomeModel.getListNovelModel());
                    break;
                case UC.NOVEL_TYPE_2_ID:
                    setType2(listNovelHomeModel.getListNovelModel());
                    setTypeNew(listNovelHomeModel.getListNovelModel());
                    break;
                case UC.NOVEL_TYPE_3_ID:
                    setType3(listNovelHomeModel.getListNovelModel());
                    break;
            }
        });
    }

    private void setSpecialType(ListNovelModel listNovelModel) {
        EpoxyHeaderItemModel_ epoxyHeaderItemModel_ = new EpoxyHeaderItemModel_();
        epoxyHeaderItemModel_.id(UUID.randomUUID().toString());
        epoxyHeaderItemModel_.setTypeName(UC.NOVEL_TYPE_SPECIAL);
        epoxyHeaderItemModel_.spanSizeOverride((totalSpanCount, position, itemCount) -> totalSpanCount);
        epoxyHeaderItemModel_.addTo(this);
        listNovelModel.getData().forEach(novelModel -> {
            EpoxyNovelModel_ model = new EpoxyNovelModel_(novelModel, v -> adapterCallbacks.novelTitleClick(novelModel));
            model.id(UUID.randomUUID().toString());
            model.addTo(this);
        });
    }

    private void setType1(ListNovelModel listNovelModel) {
        EpoxyHeaderItemModel_ epoxyHeaderItemModel_ = new EpoxyHeaderItemModel_();
        epoxyHeaderItemModel_.id(UUID.randomUUID().toString());
        epoxyHeaderItemModel_.setTypeName(UC.NOVEL_TYPE_1);
        epoxyHeaderItemModel_.spanSizeOverride((totalSpanCount, position, itemCount) -> totalSpanCount);
        epoxyHeaderItemModel_.addTo(this);
        listNovelModel.getData().forEach(novelModel -> {
            EpoxyNovelModel_ model = new EpoxyNovelModel_(novelModel, v -> adapterCallbacks.novelTitleClick(novelModel));
            model.id(UUID.randomUUID().toString());
            model.addTo(this);
        });
    }

    private void setTypeNew(ListNovelModel listNovelModel) {
        EpoxyHeaderItemModel_ epoxyHeaderItemModel1_ = new EpoxyHeaderItemModel_();
        epoxyHeaderItemModel1_.id(UUID.randomUUID().toString());
        epoxyHeaderItemModel1_.setTypeName(UC.NOVEL_TYPE_NEW);
        epoxyHeaderItemModel1_.addTo(this);


        List<EpoxyNovelModel> epoxyNovelModels = new ArrayList<>();

        listNovelModel.getData().forEach(novelModel -> {
            EpoxyNovelModel_ model = new EpoxyNovelModel_(novelModel, v -> adapterCallbacks.novelTitleClick(novelModel));
            model.id(UUID.randomUUID().toString());
            epoxyNovelModels.add(model);
        });
        setDefaultGlobalSnapHelperFactory(null);
        new CarouselModel_()
                .id(UUID.randomUUID().toString())
                .models(epoxyNovelModels)
                .padding(Carousel.Padding.dp(0, 0, 10, 0, 0))
                .addTo(this);
    }

    private void setType2(ListNovelModel listNovelModel) {
        EpoxyHeaderItemModel_ epoxyHeaderItemModel1_ = new EpoxyHeaderItemModel_();
        epoxyHeaderItemModel1_.id(UUID.randomUUID().toString());
        epoxyHeaderItemModel1_.setTypeName(UC.NOVEL_TYPE_2);
        epoxyHeaderItemModel1_.addTo(this);


        List<EpoxyNovelModel> epoxyNovelModels = new ArrayList<>();

        listNovelModel.getData().forEach(novelModel -> {
            EpoxyNovelModel_ model = new EpoxyNovelModel_(novelModel, v -> adapterCallbacks.novelTitleClick(novelModel));
            model.id(UUID.randomUUID().toString());
            epoxyNovelModels.add(model);
        });
        setDefaultGlobalSnapHelperFactory(null);
        new CarouselModel_()
                .id(UUID.randomUUID().toString())
                .models(epoxyNovelModels)
                .padding(Carousel.Padding.dp(0, 0, 10, 0, 0))
                .addTo(this);
    }

    private void setType3(ListNovelModel listNovelModel) {
        EpoxyHeaderItemModel_ epoxyHeaderItemModel2_ = new EpoxyHeaderItemModel_();
        epoxyHeaderItemModel2_.id(UUID.randomUUID().toString());
        epoxyHeaderItemModel2_.setTypeName(UC.NOVEL_TYPE_3);
        epoxyHeaderItemModel2_.spanSizeOverride((totalSpanCount, position, itemCount) -> totalSpanCount).addTo(this);
        listNovelModel.getData().forEach(novelModel -> {
            EpoxyNovelFullModel_ model = new EpoxyNovelFullModel_(novelModel, v -> adapterCallbacks.novelTitleClick(novelModel));
            model.id(UUID.randomUUID().toString());
            model.addTo(this);
        });
    }

    public interface EpoxyAdapterCallbacks {
        void novelTitleClick(NovelModel model);
    }
}