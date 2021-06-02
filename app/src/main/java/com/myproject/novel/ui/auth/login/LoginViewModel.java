package com.myproject.novel.ui.auth.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.novel.model.AuthModel;
import com.myproject.novel.model.LoginRequestModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.auth.AuthRepository;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

public class LoginViewModel extends ViewModel {

    private final AuthRepository authRepository;
    private final CompositeDisposable compositeDisposable;
    //Login
    private final MutableLiveData<AuthModel> _authModel;
    public LiveData<AuthModel> authModel;

    public LoginViewModel() {
        this.compositeDisposable = new CompositeDisposable();
        this.authRepository = new AuthRepository();
        this._authModel = new MutableLiveData<>();
        this.authModel = _authModel;
    }


    public void login(LoginRequestModel loginRequestModel) {
        this.compositeDisposable.add(authRepository.login(loginRequestModel).debounce(200, TimeUnit.MILLISECONDS).subscribeWith(loginObserve()));
    }

    private DisposableObserver<AuthModel> loginObserve() {
        return new DisposableObserver<AuthModel>() {
            @Override
            public void onNext(@NotNull AuthModel authModel) {
                Log.e("LoginError","okie");
                authModel.setCode(200);
                _authModel.setValue(authModel);
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.e("LoginError",e.getMessage());
               int code = ((HttpException) e).code();
                _authModel.setValue(new AuthModel(code));
            }

            @Override
            public void onComplete() {
                Log.e("Login","onComplete");
            }
        };
    }
}