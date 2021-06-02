package com.myproject.novel.ui.novel.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.novel.model.ChapterModel;
import com.myproject.novel.model.ListCommentModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.novel.NovelRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class DetailViewModel extends ViewModel {

    private final NovelRepository novelRepository;
    private final CompositeDisposable compositeDisposable;
    //Novel Detail
    private final MutableLiveData<NovelModel> _novelModel;
    public LiveData<NovelModel> novelModel;
    //Chapter
    private final MutableLiveData<List<ChapterModel>> _listChapter;
    public LiveData<List<ChapterModel>> listChapter;
    //Comment
    private final MutableLiveData<ListCommentModel> _listCommentModel;
    public LiveData<ListCommentModel> listCommentModel;
    //like
    private final MutableLiveData<Boolean> _like;
    public LiveData<Boolean> like;

    public DetailViewModel(int novelId, String auth) {
        //
        novelRepository = new NovelRepository();
        compositeDisposable = new CompositeDisposable();
        _novelModel = new MutableLiveData<>();
        _listChapter = new MutableLiveData<>();
        _listCommentModel = new MutableLiveData<>();
        _like = new MutableLiveData<>();
        novelModel = _novelModel;
        listChapter = _listChapter;
        listCommentModel = _listCommentModel;
        like = _like;
        //

        fetchNovelDetail(auth, novelId);
        fetchListChapter(novelId);
        fetchComments(0, 10, novelId);
    }

    private void fetchNovelDetail(String auth, int novelId) {

        compositeDisposable.add(novelRepository.fetchDetail(novelId, auth).subscribeWith(fetchNovelDetailObserver()));
    }

    private void fetchListChapter(int novelId) {
        compositeDisposable.add(novelRepository.fetchListChapter(novelId).subscribeWith(fetchListChapterObserver()));
    }

    public void fetchComments(int page, int size, int novelId) {
        compositeDisposable.add(novelRepository.fetchComments(page, size, novelId).subscribeWith(fetchCommentsObserver()));
    }

    public void rateNovel(String auth, int novelId, float star) {
        compositeDisposable.add(novelRepository.voteNovel(novelId,star, auth).subscribeWith(rateObserver()));
    }

    public void likeNovel(String auth, int novelId) {
        compositeDisposable.add(novelRepository.likeNovel(novelId, auth).subscribeWith(likeObserver()));
    }


    private DisposableObserver<NovelModel> fetchNovelDetailObserver() {
        return new DisposableObserver<NovelModel>() {
            @Override
            public void onNext(@NotNull NovelModel novelModel) {

                _novelModel.setValue(novelModel);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                e.getMessage();
            }

            @Override
            public void onComplete() {
            }
        };
    }

    private DisposableObserver<List<ChapterModel>> fetchListChapterObserver() {
        return new DisposableObserver<List<ChapterModel>>() {
            @Override
            public void onNext(@NotNull List<ChapterModel> listChapter) {
                _listChapter.setValue(listChapter);
            }

            @Override
            public void onError(@NotNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
    }

    private DisposableObserver<ListCommentModel> fetchCommentsObserver() {
        return new DisposableObserver<ListCommentModel>() {
            @Override
            public void onNext(@NotNull ListCommentModel listCommentModel) {
                _listCommentModel.setValue(listCommentModel);
            }

            @Override
            public void onError(@NotNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
    }

    private DisposableObserver<Boolean> rateObserver() {
        return new DisposableObserver<Boolean>() {
            @Override
            public void onNext(@NotNull Boolean aBoolean) {

            }

            @Override
            public void onError(@NotNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }


    private DisposableObserver<Boolean> likeObserver() {
        return new DisposableObserver<Boolean>() {
            @Override
            public void onNext(@NotNull Boolean aBoolean) {
                _like.setValue(aBoolean);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                _like.setValue(false);
            }

            @Override
            public void onComplete() {

            }
        };
    }
}