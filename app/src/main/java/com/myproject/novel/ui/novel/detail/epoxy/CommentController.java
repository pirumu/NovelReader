package com.myproject.novel.ui.novel.detail.epoxy;

import com.airbnb.epoxy.TypedEpoxyController;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.CommentModel;
import com.myproject.novel.ui.home.epoxy.EpoxyHeaderItemModel_;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentController extends TypedEpoxyController<List<CommentModel>> {

    private final CommentController.EpoxyAdapterCallbacks adapterCallbacks;
    private final int novelId;

    public CommentController(CommentController.EpoxyAdapterCallbacks epoxyAdapterCallbacks, int novelId) {
        this.adapterCallbacks = epoxyAdapterCallbacks;
        this.novelId = novelId;
    }

    @Override
    protected void buildModels(List<CommentModel> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        if (data.isEmpty()) {
            EpoxyNoCommentModel_ encm = new EpoxyNoCommentModel_(this.novelId, v -> adapterCallbacks.noCommentClick());
            encm.id(UUID.randomUUID().toString());
            encm.addTo(this);
        } else {

            EpoxyHeaderItemModel_ epoxyHeaderItemModel_ = new EpoxyHeaderItemModel_();
            epoxyHeaderItemModel_.id(UUID.randomUUID().toString());
            epoxyHeaderItemModel_.setTypeName(UC.TOP_COMMENT);
            epoxyHeaderItemModel_.addTo(this);

            data.forEach(commentModel -> {
                EpoxyCommentModel_ ecm = new EpoxyCommentModel_(commentModel, v -> adapterCallbacks.commentClick(commentModel));
                ecm.id(commentModel.getCommentId()).addTo(this);

            });
            EpoxyAllCommentModel_ eacm = new EpoxyAllCommentModel_(120, v -> adapterCallbacks.commentClick(null));
            eacm.id(UUID.randomUUID().toString());
            eacm.addTo(this);

        }
    }

    public interface EpoxyAdapterCallbacks {
        void commentClick(CommentModel model);

        void noCommentClick();
    }

}
