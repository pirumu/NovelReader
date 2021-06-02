package com.myproject.novel.ui.tag;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.novel.model.TagModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class TagViewModel extends ViewModel {
    private final CompositeDisposable compositeDisposable;
    private final TagRepository filterRepository;
    private final MutableLiveData<List<TagModel>> _tagModelList;
    public LiveData<List<TagModel>> tagModelList;

    public TagViewModel() {
        this.compositeDisposable = new CompositeDisposable();
        this.filterRepository = new TagRepository();
        _tagModelList = new MutableLiveData<>();
        tagModelList = _tagModelList;
        fetchTag();
    }

    public void fetchTag() {
        compositeDisposable.add(filterRepository.fetchListTag().subscribeWith(fetchTagObserver()));
    }

    private DisposableObserver<List<TagModel>> fetchTagObserver() {
        return new DisposableObserver<List<TagModel>>() {
            @Override
            public void onNext(@NotNull List<TagModel> listTagModel) {
                _tagModelList.setValue(listTagModel);
            }

            @Override
            public void onError(@NotNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
    }
}