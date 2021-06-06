package com.myproject.novel.ui.novel.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.novel.model.ChapterModel;
import com.myproject.novel.model.CommentModel;
import com.myproject.novel.model.ListCommentModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.model.ReplyCommentRequestModel;
import com.myproject.novel.ui.novel.NovelRepository;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

@SuppressWarnings("deprecation")
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

    //like
    private final MutableLiveData<Boolean> _likeComment;
    public LiveData<Boolean> likeComment;

    //comment

    private final MutableLiveData<Boolean> _commentNovel;
    public LiveData<Boolean> commentNovel;

    private final MutableLiveData<List<CommentModel>> _replyList;
    public LiveData<List<CommentModel>> replyList;


    private final MutableLiveData<List<NovelModel>> _listSuggest;
    public LiveData<List<NovelModel>> listSuggest;

    private final int _novelId, _tagId;

    public DetailViewModel(int tagId, int novelId, String auth) {
        //
        _novelId = novelId;
        _tagId = tagId;
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
        _likeComment = new MutableLiveData<>();
        likeComment = _like;
        _commentNovel = new MutableLiveData<>();
        commentNovel = _commentNovel;

        _replyList = new MutableLiveData<>();
        replyList = _replyList;
        _listSuggest = new MutableLiveData<>();
        listSuggest = _listSuggest;


        fetchNovelDetail(auth, novelId);
        fetchListChapter(novelId);
//        fetchComments(0, 10, novelId);
    }


    public void fetchNovelSuggest(int tagId) {
        compositeDisposable.add(novelRepository.getList(tagId, _novelId).subscribeWith(fetchSuggestListObserver()));
    }

    public void fetchNovelDetail(String auth, int novelId) {

        compositeDisposable.add(novelRepository.fetchDetail(novelId, auth).subscribeWith(fetchNovelDetailObserver()));
    }

    public void fetchListChapter(int novelId) {
        compositeDisposable.add(novelRepository.fetchListChapter(novelId).subscribeWith(fetchListChapterObserver()));
    }

    public void fetchComments(int page, int size, int novelId) {
        compositeDisposable.add(novelRepository.fetchComments(page, size, novelId).subscribeWith(fetchCommentsObserver()));
    }

    public void rateNovel(String auth, int novelId, float star) {
        compositeDisposable.add(novelRepository.voteNovel(novelId, star, auth).subscribeWith(rateObserver()));
    }

    public void likeNovel(String auth, int novelId) {
        compositeDisposable.add(novelRepository.likeNovel(novelId, auth).subscribeWith(likeNovelObserver()));
    }

    public void likeCommentUser(String auth, int commentId) {
        compositeDisposable.add(novelRepository.likeComment(commentId, auth).subscribeWith(likeCommentObserver()));
    }

    public void commentUserNovel(String auth, int commentId, ReplyCommentRequestModel replyCommentRequestModel) {
        compositeDisposable.add(novelRepository.replyComment(replyCommentRequestModel, commentId, auth).subscribeWith(commentNovelObserver()));
    }

    public void fetchReplyList(String auth, int commentId) {
        if (commentId == -1) {
            compositeDisposable.add(novelRepository.getListComment(this._novelId, auth).subscribeWith(fetchReplyListObserver()));

        } else {
            compositeDisposable.add(novelRepository.getListReplyComment(commentId, auth).subscribeWith(fetchReplyListObserver()));

        }
    }


    private DisposableObserver<NovelModel> fetchNovelDetailObserver() {
        return new DisposableObserver<NovelModel>() {
            @Override
            public void onNext(@NotNull NovelModel novelModel) {

                _novelModel.setValue(novelModel);
            }

            @Override
            public void onError(@NotNull Throwable e) {

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


    private DisposableObserver<Boolean> likeNovelObserver() {
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

    private DisposableObserver<Boolean> likeCommentObserver() {
        return new DisposableObserver<Boolean>() {
            @Override
            public void onNext(@NotNull Boolean aBoolean) {
                _likeComment.setValue(aBoolean);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                _likeComment.setValue(false);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private DisposableObserver<Boolean> commentNovelObserver() {
        return new DisposableObserver<Boolean>() {
            @Override
            public void onNext(@NotNull Boolean aBoolean) {
                _commentNovel.setValue(aBoolean);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                _commentNovel.setValue(false);
            }

            @Override
            public void onComplete() {

            }
        };
    }


    private DisposableObserver<List<CommentModel>> fetchReplyListObserver() {
        return new DisposableObserver<List<CommentModel>>() {
            @Override
            public void onNext(@NotNull List<CommentModel> commentModels) {
                _replyList.setValue(commentModels);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                _replyList.setValue(new ArrayList<>());
            }

            @Override
            public void onComplete() {

            }
        };
    }


    private DisposableObserver<List<NovelModel>> fetchSuggestListObserver() {
        return new DisposableObserver<List<NovelModel>>() {
            @Override
            public void onNext(@NotNull List<NovelModel> list) {
                _listSuggest.setValue(list);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                _listSuggest.setValue(new ArrayList<>());
            }

            @Override
            public void onComplete() {

            }
        };
    }

}