package com.myproject.novel.net.service;

import com.myproject.novel.model.BannerModel;
import com.myproject.novel.model.ChapterModel;
import com.myproject.novel.model.ListCommentModel;
import com.myproject.novel.model.ListNovelModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.model.TagModel;
import com.myproject.novel.model.VoteRequestModel;
import com.myproject.novel.net.C;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NovelService {
    @GET(C.NOVELS)
    Observable<ListNovelModel> getList(@Query("offset") Integer page, @Query("limit") Integer size, @Query("type") String type);

    @GET(C.NOVELS)
    Observable<ListNovelModel> getList(@Query("type") int type);

    @GET(C.SEARCH)
    Observable<List<NovelModel>> search(@Query("novelTitle") String novelTitle);

    @GET(C.NOVEL_DETAIL)
    Observable<NovelModel> getDetail(@Path("novelId") Integer novelId, @Header("Authorization") String auth);

    @GET(C.NOVEL_CHAPTER)
    Observable<List<ChapterModel>> getListChapter(@Path("novelId") Integer novelId);


    @GET(C.COMMENTS)
    Observable<ListCommentModel> getListComment(@Path("novelId") Integer novelId, @Query("offset") Integer page, @Query("limit") Integer size);


    @GET(C.BANNERS)
    Observable<List<BannerModel>> getBanners();

    @GET(C.TAG)
    Observable<List<TagModel>> getTag();


    @POST(C.VOTE)
    Observable<Boolean> vote(@Path("novelId") Integer novelId,@Body() VoteRequestModel voteRequestModel, @Header("Authorization") String auth);

    @POST(C.LIKE)
    Observable<Boolean> like(@Path("novelId") Integer novelId, @Header("Authorization") String auth);
}
