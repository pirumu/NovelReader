package com.myproject.novel.net.service;

import com.myproject.novel.model.AuthModel;
import com.myproject.novel.model.ForgotPasswordModel;
import com.myproject.novel.model.LoginRequestModel;
import com.myproject.novel.model.RegisterRequestModel;
import com.myproject.novel.model.UserModel;
import com.myproject.novel.net.C;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST(C.LOGIN)
    Observable<AuthModel> login(@Body() LoginRequestModel loginRequestModel);

    @POST(C.LOGIN)
    Observable<UserModel> register(@Body() RegisterRequestModel registerRequestModel);

    @POST(C.FORGOT_PASSWORD)
    Observable<Boolean> forgotPassword(@Body() ForgotPasswordModel forgotPasswordModel);


}
