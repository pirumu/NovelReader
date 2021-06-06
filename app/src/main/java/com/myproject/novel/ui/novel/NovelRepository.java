package com.myproject.novel.ui.novel;

import com.myproject.novel.model.ChapterModel;
import com.myproject.novel.model.CommentModel;
import com.myproject.novel.model.ListCommentModel;
import com.myproject.novel.model.ListNovelModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.model.ReplyCommentRequestModel;
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
        auth = "Bearer " + auth;
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
        auth = "Bearer " + auth;
        return novelService.vote(novelId, new VoteRequestModel(vote), auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> likeNovel(int novelId, String auth) {
        auth = "Bearer " + auth;
        return novelService.like(novelId, auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<ListNovelModel> fetchListNovelByTag(int offset, int limit, int tagId) {
        offset = (offset - 1) * limit;
        return novelService.getList(offset, limit, tagId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ListNovelModel> fetchListNovelFavorite(int offset, int limit, String auth) {
        offset = (offset - 1) * limit;
        auth = "Bearer " + auth;
        return novelService.getFavoriteList(offset, limit, auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> likeComment(int commentId, String auth) {
        auth = "Bearer " + auth;
        return novelService.likeComment(commentId, auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Boolean> replyComment(ReplyCommentRequestModel r, int commentId, String auth) {
        auth = "Bearer " + auth;
        return novelService.replyComment(r, commentId, auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<List<CommentModel>> getListReplyComment(int commentId, String auth) {
        auth = "Bearer " + auth;
        return novelService.getListReplyComment(commentId, auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<CommentModel>> getListComment(int novelId, String auth) {
        auth = "Bearer " + auth;
        return novelService.getListReplyComment(-1, auth, novelId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<List<NovelModel>> getList(int tagId, int novelId) {
        return novelService.getList(tagId, novelId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
