package com.myproject.novel.model;

public class ProfileModel {
    private int userProfileId;
    private int userId;
    private String nickname;
    private int gender;
    private String birthday;
    private String avatar;

    public ProfileModel() {

    }

    public ProfileModel(int userProfileId, int userId, String nickname, int gender, String birthday, String avatar) {
        this.userProfileId = userProfileId;
        this.userId = userId;
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        this.avatar = avatar;
    }


    public int getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(int userProfileId) {
        this.userProfileId = userProfileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
