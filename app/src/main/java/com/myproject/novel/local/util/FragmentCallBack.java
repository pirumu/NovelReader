package com.myproject.novel.local.util;

import android.view.View;

import androidx.lifecycle.LiveData;

import com.myproject.novel.model.CommentModel;
import com.myproject.novel.model.ReplyCommentRequestModel;

import java.util.List;

public interface FragmentCallBack {
    void voteNovel(float start);

    LiveData<Boolean> commentNovel(int parentId, ReplyCommentRequestModel replyCommentRequestModel);


    LiveData<List<CommentModel>> getListReply(int parentId);

    void commentClick(CommentModel model);

    void likeComment(View v, CommentModel model);

    void reloadActivity();
}