package com.myproject.novel.net.service;

import com.myproject.novel.model.ProfileModel;
import com.myproject.novel.net.C;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {

    @Multipart
    @POST(C.USER_PROFILE)
    Observable<ProfileModel> updateProfile(
            @Part("nickname") RequestBody nickname,
            @Part("gender") RequestBody gender,
            @Part("birthday") RequestBody birthday,
            @Part MultipartBody.Part avatar,
            @Header("Authorization") String auth
    );

    @POST(C.USER_PROFILE)
    Observable<ProfileModel> updateProfile(
            @Body ProfileModel profile,
            @Header("Authorization") String auth
    );
}
