package com.myproject.novel.ui.filter.epoxy;

import android.util.Log;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.EpoxyController;
import com.google.gson.Gson;
import com.myproject.novel.model.ListNovelModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.filter.LoadMoreRecyclerViewScrollListener;
import com.myproject.novel.ui.filter.LoadingView_;
import com.myproject.novel.ui.home.epoxy.EpoxyNovelFullModel_;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FilterController extends EpoxyController {

    private final EpoxyAdapterCallbacks clickable;
    private int page;

    private final LoadMoreRecyclerViewScrollListener listener;

    private ListNovelModel listNovelModel;


    @AutoModel
    LoadingView_ loadingView;

    public FilterController(EpoxyAdapterCallbacks clickable, LoadMoreRecyclerViewScrollListener listener, int startPage) {
        this.listener = listener;
        this.page = startPage;
        this.clickable = clickable;
    }

    public void nextPage() {
        page += 1;
    }

    public int getPage() {
        return page;
    }


    @Override
    protected void buildModels() {
        if (listNovelModel != null && listNovelModel.getData() != null) {
            ArrayList<NovelModel> list = (ArrayList<NovelModel>) listNovelModel.getData();
            list.sort((o1, o2) -> o2.getNovelId() - o1.getNovelId());
            list.forEach(i -> {
                Log.e("NovelId", String.valueOf(i.getNovelId()));
                EpoxyNovelFullModel_ enfm = new EpoxyNovelFullModel_(i, v -> clickable.novelTitleClick(i));
                enfm.id(UUID.randomUUID().toString());
                enfm.addTo(this);
            });
        }


        loadingView.addIf(() -> listener.hasMoreToLoad(), this);
    }

    public ListNovelModel getListNovelModel() {
        return listNovelModel;
    }

    public void setListNovelModel(ListNovelModel listNovelModel) {

        Set<NovelModel> set = new HashSet<>(listNovelModel.getData());
        listNovelModel.setData(new ArrayList<>(set));
        this.listNovelModel = listNovelModel;
        Gson gson = new Gson();
        Log.e("DebugEpoxyData", gson.toJson(listNovelModel.getMeta()));
        requestModelBuild();
    }

    public interface EpoxyAdapterCallbacks {
        void novelTitleClick(NovelModel model);
    }


}
