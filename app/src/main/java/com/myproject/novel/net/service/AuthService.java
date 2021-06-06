package com.myproject.novel.net.service;

import com.myproject.novel.model.AuthModel;
import com.myproject.novel.model.ForgotPasswordModel;
import com.myproject.novel.model.LoginRequestModel;
import com.myproject.novel.model.ProfileModel;
import com.myproject.novel.model.RegisterRequestModel;
import com.myproject.novel.model.ResetPasswordResponseModel;
import com.myproject.novel.model.UserModel;
import com.myproject.novel.net.C;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AuthService {

    @POST(C.LOGIN)
    Observable<AuthModel> login(@Body() LoginRequestModel loginRequestModel);

    @POST(C.REGISTER)
    Observable<UserModel> register(@Body() RegisterRequestModel registerRequestModel);

    @PUT(C.FORGOT_PASSWORD)
    Observable<ResetPasswordResponseModel> forgotPassword(@Body() ForgotPasswordModel forgotPasswordModel);

    @GET(C.USER_INFO)
    Observable<ProfileModel> getInfo(@Header("Authorization") String auth);

}
