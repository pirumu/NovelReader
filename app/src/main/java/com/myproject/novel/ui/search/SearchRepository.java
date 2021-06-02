package com.myproject.novel.ui.search;

import com.myproject.novel.model.NovelModel;
import com.myproject.novel.net.HttpClient;
import com.myproject.novel.net.service.NovelService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SearchRepository {

    NovelService novelService;

    public SearchRepository() {
        Retrofit retrofit = HttpClient.getClient();
        novelService = retrofit.create(NovelService.class);
    }

    public Observable<List<NovelModel>> search(String novelTitle) {
        return novelService.search(novelTitle).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}

