package com.myproject.novel.ui.filter;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.novel.model.ListNovelModel;
import com.myproject.novel.ui.novel.NovelRepository;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class FilterViewModel extends ViewModel {

    private final NovelRepository novelRepository;
    public final CompositeDisposable compositeDisposable;
    //novel
    private final MutableLiveData<ListNovelModel> _novelList;
    public LiveData<ListNovelModel> novelList;


    private DisposableObserver<ListNovelModel> current;
    private int tagId;


    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public FilterViewModel(int tag) {
        this.novelRepository = new NovelRepository();
        this.compositeDisposable = new CompositeDisposable();
        this._novelList = new MutableLiveData<>();
        this.novelList = this._novelList;
        this.tagId = tag;
    }

    public void fetchNovelList(int page) {
        Log.e("current call view model", String.valueOf(page));
        current = novelRepository.fetchListNovelByTag(page, 20, this.tagId).subscribeWith(fetchNovelListObserver());
        compositeDisposable.add(current);
    }

    private DisposableObserver<ListNovelModel> fetchNovelListObserver() {
        return new DisposableObserver<ListNovelModel>() {
            @Override
            public void onNext(@NotNull ListNovelModel listNovelModel) {
                _novelList.setValue(listNovelModel);
            }

            @Override
            public void onError(@NotNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
    }

    public DisposableObserver<ListNovelModel> getCurrent() {
        return current;
    }

}