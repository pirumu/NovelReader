package com.myproject.novel.net;

public class C {

    public static final String BASE_URL = "https://api-novel.vnat.xyz";

    public static final String LOGIN = "/api/login";
    public static final String REGISTER = "/api/register";
    public static final String FORGOT_PASSWORD = "/api/forget-password";

    //USER    public static final String USER_INFO_API = "";

    //Novel

    public static final String NOVELS = "/api/novel";
    public static final String NOVEL_DETAIL = "/api/novel/{novelId}";
    public static final String NOVEL_CHAPTER = "/api/novel/chapter/{novelId}?select=true";

    //Comment

    public static final String COMMENTS = "/api/novel/comment/{novelId}";

    //Banners
    public static final String BANNERS = "/api/banner";
    // Tags
    public static final String TAG = "/api/tag";

    // Search
    public static final String SEARCH = "/api/search";

    // vote
    public static final String VOTE = "/api/novel/vote/{novelId}";
    // vote
    public static final String LIKE = "/api/novel/like/{novelId}";
}
