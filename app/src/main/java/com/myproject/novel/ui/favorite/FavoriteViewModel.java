package com.myproject.novel.ui.favorite;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.novel.model.ListNovelModel;
import com.myproject.novel.model.MetaDataModel;
import com.myproject.novel.ui.novel.NovelRepository;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class FavoriteViewModel extends ViewModel {
    private final NovelRepository novelRepository;
    public final CompositeDisposable compositeDisposable;
    //novel
    private final MutableLiveData<ListNovelModel> _novelList;
    public LiveData<ListNovelModel> novelList;

    private final String auth;
    private DisposableObserver<ListNovelModel> current;

    public FavoriteViewModel(String auth) {
        this.novelRepository = new NovelRepository();
        this.compositeDisposable = new CompositeDisposable();
        this._novelList = new MutableLiveData<>();
        this.novelList = this._novelList;
        this.auth = auth;

    }

    public void fetchNovelList(int page) {
        Log.e("current call view model", String.valueOf(page));
        current = novelRepository.fetchListNovelFavorite(page, 20, auth).subscribeWith(fetchNovelListObserver());
        compositeDisposable.add(current);
    }

    private DisposableObserver<ListNovelModel> fetchNovelListObserver() {
        return new DisposableObserver<ListNovelModel>() {
            @Override
            public void onNext(@NotNull ListNovelModel listNovelModel) {
                if (listNovelModel.getMeta() == null) {
                    listNovelModel.setMeta(new MetaDataModel(20, 0, 0, 0));
                }
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