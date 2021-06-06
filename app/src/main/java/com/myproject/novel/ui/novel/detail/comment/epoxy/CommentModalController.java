package com.myproject.novel.ui.novel.detail.comment.epoxy;

import android.view.View;

import com.airbnb.epoxy.Typed2EpoxyController;
import com.myproject.novel.model.CommentModel;

import java.util.List;

public class CommentModalController extends Typed2EpoxyController<List<CommentModel>, Boolean> {

    private final CommentModalController.EpoxyAdapterCallbacks cb;

    public CommentModalController(CommentModalController.EpoxyAdapterCallbacks c) {
        cb = c;
    }

    @Override
    protected void buildModels(List<CommentModel> data, Boolean isReply) {
        data.forEach(i -> {
            boolean check = i.getParentId() != 0;
            EpoxyCommentReplyModel_ cm = new EpoxyCommentReplyModel_(i, check);
            if (!check) {
                cm.setLikeComment(v -> cb.likeComment(v, i));
                cm.setReplyComment(v -> cb.commentClick(i));
            }
            cm.id(i.getCommentId());
            cm.addTo(this);
        });
    }

    public interface EpoxyAdapterCallbacks {
        void commentClick(CommentModel model);

        void replyComment(CommentModel model);

        void likeComment(View v, CommentModel model);

        void noCommentClick();
    }
}
