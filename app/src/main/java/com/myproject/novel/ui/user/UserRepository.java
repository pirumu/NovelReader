package com.myproject.novel.ui.user;

import com.myproject.novel.model.ProfileModel;
import com.myproject.novel.net.HttpClient;
import com.myproject.novel.net.service.UserService;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class UserRepository {


    UserService userService;

    public UserRepository() {
        Retrofit retrofit = HttpClient.getClient();
        userService = retrofit.create(UserService.class);
    }


    public Observable<ProfileModel> updateProfile(ProfileModel profileModel, String realPath, String auth) {
        auth = "Bearer " + auth;

        if (realPath == null) {
            return userService.updateProfile(profileModel, auth).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        File file = new File(realPath);

        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part avatar = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RequestBody nickname = RequestBody.create(profileModel.getNickname(), MediaType.parse("multipart/form-data"));
        RequestBody gender = RequestBody.create(String.valueOf(profileModel.getGender()), MediaType.parse("multipart/form-data"));
        RequestBody birthday = RequestBody.create(profileModel.getBirthday(), MediaType.parse("multipart/form-data"));

        return userService.updateProfile(nickname, gender, birthday, avatar, auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
