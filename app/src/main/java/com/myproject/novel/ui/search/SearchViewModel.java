package com.myproject.novel.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.novel.model.NovelModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class SearchViewModel extends ViewModel {
    private final SearchRepository searchRepository;


    private final CompositeDisposable compositeDisposable;
    //novel
    private final MutableLiveData<List<NovelModel>> _novelList;

    public LiveData<List<NovelModel>> novelList;

    public SearchViewModel() {
        _novelList = new MutableLiveData<>();
        novelList = _novelList;
        compositeDisposable = new CompositeDisposable();
        searchRepository = new SearchRepository();

    }

    public void search(String novelTitle) {
        compositeDisposable.add(searchRepository.search(novelTitle).subscribeWith(searchObserver()));
    }

    public DisposableObserver<List<NovelModel>> searchObserver() {
        return new DisposableObserver<List<NovelModel>>() {
            @Override
            public void onNext(@NotNull List<NovelModel> novelModels) {
                _novelList.setValue(novelModels);
            }

            @Override
            public void onError(@NotNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

}
