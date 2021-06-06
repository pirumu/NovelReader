package com.myproject.novel.ui.auth.forgot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.novel.model.ForgotPasswordModel;
import com.myproject.novel.model.ResetPasswordResponseModel;
import com.myproject.novel.ui.auth.AuthRepository;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class ForgotViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final CompositeDisposable compositeDisposable;
    //Login
    private final MutableLiveData<ResetPasswordResponseModel> _rsp;
    public LiveData<ResetPasswordResponseModel> rsp;

    public ForgotViewModel() {
        authRepository = new AuthRepository();
        compositeDisposable = new CompositeDisposable();
        _rsp = new MutableLiveData<>();
        rsp = _rsp;

    }


    public void resetPassword(ForgotPasswordModel forgotPasswordModel) {
        compositeDisposable.add(authRepository.forgotPassword(forgotPasswordModel).subscribeWith(resetPasswordObserver()));
    }

    public DisposableObserver<ResetPasswordResponseModel> resetPasswordObserver() {
        return new DisposableObserver<ResetPasswordResponseModel>() {
            @Override
            public void onNext(@NotNull ResetPasswordResponseModel res) {
                _rsp.setValue(res);
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