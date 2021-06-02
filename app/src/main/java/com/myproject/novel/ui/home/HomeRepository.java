package com.myproject.novel.ui.home;

import com.myproject.novel.model.BannerModel;
import com.myproject.novel.model.ListNovelModel;
import com.myproject.novel.net.HttpClient;
import com.myproject.novel.net.service.NovelService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class HomeRepository {

    NovelService novelService;

    public HomeRepository() {
        Retrofit retrofit = HttpClient.getClient();
        novelService = retrofit.create(NovelService.class);
    }

    public Observable<ListNovelModel> fetchListNovel(int type) {
        return novelService.getList(type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<BannerModel>> fetchListBanner() {
        return novelService.getBanners().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
