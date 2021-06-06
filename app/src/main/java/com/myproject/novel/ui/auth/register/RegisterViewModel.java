package com.myproject.novel.ui.auth.register;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myproject.novel.model.RegisterRequestModel;
import com.myproject.novel.model.UserModel;
import com.myproject.novel.ui.auth.AuthRepository;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

public class RegisterViewModel extends ViewModel {

    private final AuthRepository authRepository;
    private final CompositeDisposable compositeDisposable;
    //Login
    private final MutableLiveData<UserModel> _userModel;
    public LiveData<UserModel> userModel;


    public RegisterViewModel() {
        this.compositeDisposable = new CompositeDisposable();
        this.authRepository = new AuthRepository();
        this._userModel = new MutableLiveData<>();

        this.userModel = _userModel;
    }


    public void register(RegisterRequestModel registerRequestModel) {
        this.compositeDisposable.add(authRepository.register(registerRequestModel).debounce(200, TimeUnit.MILLISECONDS).subscribeWith(registerObserve()));
    }

    private DisposableObserver<UserModel> registerObserve() {
        return new DisposableObserver<UserModel>() {
            @Override
            public void onNext(@NotNull UserModel userModel) {
                _userModel.setValue(userModel);
            }

            @Override
            public void onError(@NotNull Throwable e) {

                int code = ((HttpException) e).code();
                Log.e("RegisterError", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i("Register", "onComplete");
            }
        };
    }

}