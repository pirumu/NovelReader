package com.myproject.novel.ui.novel;

import com.myproject.novel.model.ChapterModel;
import com.myproject.novel.model.ListCommentModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.model.VoteRequestModel;
import com.myproject.novel.net.HttpClient;
import com.myproject.novel.net.service.NovelService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NovelRepository {

    NovelService novelService;

    public NovelRepository() {
        Retrofit retrofit = HttpClient.getClient();
        novelService = retrofit.create(NovelService.class);
    }

    public Observable<NovelModel> fetchDetail(int novelId, String auth) {
        auth = "Bearer "+auth;
        return novelService.getDetail(novelId, auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<ChapterModel>> fetchListChapter(int novelId) {
        return novelService.getListChapter(novelId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<ListCommentModel> fetchComments(int page, int size, int novelId) {
        return novelService.getListComment(novelId, page, size).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Boolean> voteNovel(int novelId, float vote, String auth) {
        auth = "Bearer "+auth;
        return novelService.vote(novelId, new VoteRequestModel(vote), auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> likeNovel(int novelId, String auth) {
        auth = "Bearer "+auth;
        return novelService.like(novelId, auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
