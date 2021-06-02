package com.myproject.novel.ui.tag;

import com.myproject.novel.model.TagModel;
import com.myproject.novel.net.HttpClient;
import com.myproject.novel.net.service.NovelService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class TagRepository {

    NovelService novelService;

    public TagRepository() {
        Retrofit retrofit = HttpClient.getClient();
        novelService = retrofit.create(NovelService.class);
    }

    public Observable<List<TagModel>> fetchListTag() {
        return novelService.getTag().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
