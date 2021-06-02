package com.myproject.novel.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.BannerModel;
import com.myproject.novel.model.ListNovelModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class HomeViewModel extends ViewModel {

    private final HomeRepository homeRepository;
    private final CompositeDisposable compositeDisposable;
    //novel
    private final MutableLiveData<ListNovelModel> _novelList1;
    private final MutableLiveData<ListNovelModel> _novelList2;
    private final MutableLiveData<ListNovelModel> _novelList3;
    public LiveData<ListNovelModel> novelList1;
    public LiveData<ListNovelModel> novelList2;
    public LiveData<ListNovelModel> novelList3;

    // banner
    private final MutableLiveData<List<BannerModel>> _bannerList;
    public LiveData<List<BannerModel>> bannerList;

    public HomeViewModel() {
        this.homeRepository = new HomeRepository();
        this.compositeDisposable = new CompositeDisposable();

        this._novelList1 = new MutableLiveData<>();
        this._novelList2 = new MutableLiveData<>();
        this._novelList3 = new MutableLiveData<>();
        this.novelList1 = this._novelList1;
        this.novelList2 = this._novelList2;
        this.novelList3 = this._novelList3;


        this._bannerList = new MutableLiveData<>();
        this.bannerList = _bannerList;

        fetchBanners();
        fetchNovelList();
    }

    public void fetchNovelList() {
        compositeDisposable.add(homeRepository.fetchListNovel(UC.NOVEL_TYPE_1_ID).subscribeWith(fetchNovelListObserver(UC.NOVEL_TYPE_1_ID)));
        compositeDisposable.add(homeRepository.fetchListNovel(UC.NOVEL_TYPE_2_ID).subscribeWith(fetchNovelListObserver(UC.NOVEL_TYPE_2_ID)));
        compositeDisposable.add(homeRepository.fetchListNovel(UC.NOVEL_TYPE_3_ID).subscribeWith(fetchNovelListObserver(UC.NOVEL_TYPE_3_ID)));
    }

    public void fetchBanners() {
        compositeDisposable.add(homeRepository.fetchListBanner().subscribeWith(fetchBannersObserver()));
    }

    private DisposableObserver<List<BannerModel>> fetchBannersObserver() {
        return new DisposableObserver<List<BannerModel>>() {
            @Override
            public void onNext(@NotNull List<BannerModel> listCommentModel) {
                _bannerList.setValue(listCommentModel);
            }

            @Override
            public void onError(@NotNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        };
    }

    private DisposableObserver<ListNovelModel> fetchNovelListObserver(int type) {
        return new DisposableObserver<ListNovelModel>() {
            @Override
            public void onNext(@NotNull ListNovelModel listNovelModel) {
                switch (type) {
                    case UC.NOVEL_TYPE_1_ID:
                        _novelList1.setValue(listNovelModel);
                        break;
                    case UC.NOVEL_TYPE_2_ID:
                        _novelList2.setValue(listNovelModel);
                        break;
                    case UC.NOVEL_TYPE_3_ID:
                        _novelList3.setValue(listNovelModel);
                        break;

                }
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