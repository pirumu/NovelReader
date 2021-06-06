package com.myproject.novel.ui.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.novel.model.ProfileModel;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class UserViewModel extends ViewModel {
    private final UserRepository userRepository;
    public final CompositeDisposable compositeDisposable;
    //novel
    private final MutableLiveData<ProfileModel> _profileModel;
    public LiveData<ProfileModel> profileModel;


    public UserViewModel() {
        compositeDisposable = new CompositeDisposable();
        userRepository = new UserRepository();
        _profileModel = new MutableLiveData<>();
        profileModel = _profileModel;
    }


    public void updateInfo(ProfileModel pm, String realPath, String auth) {
        compositeDisposable.add(userRepository.updateProfile(pm, realPath, auth).subscribeWith(updateInfoObserver()));
    }

    public void updateInfo(ProfileModel pm, String auth) {
        compositeDisposable.add(userRepository.updateProfile(pm, null, auth).subscribeWith(updateInfoObserver()));
    }

    public DisposableObserver<ProfileModel> updateInfoObserver() {
        return new DisposableObserver<ProfileModel>() {
            @Override
            public void onNext(@NotNull ProfileModel profileModel) {
                _profileModel.setValue(profileModel);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
    }
}