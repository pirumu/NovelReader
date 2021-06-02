package com.myproject.novel.ui.auth;

import com.myproject.novel.model.AuthModel;
import com.myproject.novel.model.ForgotPasswordModel;
import com.myproject.novel.model.LoginRequestModel;
import com.myproject.novel.model.RegisterRequestModel;
import com.myproject.novel.model.UserModel;
import com.myproject.novel.net.HttpClient;
import com.myproject.novel.net.service.AuthService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AuthRepository {

    AuthService authService;

    public AuthRepository() {
        Retrofit retrofit = HttpClient.getClient();
        authService = retrofit.create(AuthService.class);
    }

    public Observable<AuthModel> login(LoginRequestModel loginRequestModel) {
        return authService.login(loginRequestModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<UserModel> register(RegisterRequestModel registerRequestModel) {
        return authService.register(registerRequestModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Boolean> forgotPassword(ForgotPasswordModel forgotPasswordModel) {
        return authService.forgotPassword(forgotPasswordModel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
