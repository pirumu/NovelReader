package com.myproject.novel.ui.novel.detail.epoxy;

import com.airbnb.epoxy.TypedEpoxyController;
import com.myproject.novel.model.CommentModel;
import com.myproject.novel.model.UserModel;
import com.myproject.novel.ui.home.epoxy.EpoxyHeaderItemModel_;

import java.util.List;

public class CommentController  extends TypedEpoxyController<List<CommentModel>> {

    private final CommentController.EpoxyAdapterCallbacks adapterCallbacks;

    public CommentController(CommentController.EpoxyAdapterCallbacks epoxyAdapterCallbacks) {
        this.adapterCallbacks = epoxyAdapterCallbacks;
    }

    @Override
    protected void buildModels(List<CommentModel> data) {
        EpoxyHeaderItemModel_ epoxyHeaderItemModel_ =  new EpoxyHeaderItemModel_();
        epoxyHeaderItemModel_.id("tbl");
        epoxyHeaderItemModel_.setTypeName("Top Bình Luận");
        epoxyHeaderItemModel_.addTo(this);
        UserModel userModel = new UserModel(1,"Thế Văn","https://avatars.githubusercontent.com/u/68113143?s=60&v=4");
        data.forEach( commentModel -> {
            EpoxyCommentModel_ ecm = new EpoxyCommentModel_(userModel,commentModel,v->adapterCallbacks.commentClick(commentModel));
            ecm.id(commentModel.commentId).addTo(this);

        });

        EpoxyAllCommentModel_ eacm = new EpoxyAllCommentModel_(120,v->adapterCallbacks.commentClick(data.get(0)));
        eacm.id("all_comment");
        eacm.addTo(this);

    }

    public interface EpoxyAdapterCallbacks {
        void commentClick(CommentModel model);
    }

}
