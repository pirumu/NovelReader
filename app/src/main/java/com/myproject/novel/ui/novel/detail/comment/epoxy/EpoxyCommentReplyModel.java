package com.myproject.novel.ui.novel.detail.comment.epoxy;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.GlideApp;
import com.myproject.novel.model.CommentModel;

import de.hdodenhof.circleimageview.CircleImageView;

@EpoxyModelClass
public abstract class EpoxyCommentReplyModel extends EpoxyModelWithHolder<EpoxyCommentReplyModel.EpoxyCommentHolder> {


    @Override
    protected int getDefaultLayout() {
        return R.layout.comment_item;
    }

    @EpoxyAttribute
    public CommentModel commentModel;

    @EpoxyAttribute
    public boolean isReply;

    @EpoxyAttribute
    public View.OnClickListener likeComment;

    @EpoxyAttribute
    public View.OnClickListener replyComment;


    public EpoxyCommentReplyModel(CommentModel commentModel, boolean ir) {
        this.commentModel = commentModel;
        this.isReply = ir;
    }


    @Override
    public void bind(@NonNull EpoxyCommentReplyModel.EpoxyCommentHolder holder) {
        super.bind(holder);

        GlideApp.with(holder.userAvatar.getContext()).asBitmap().load(commentModel.getAvatar())
                .placeholder(CommonUtils.shimmerEffect())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, Transition<? super Bitmap> transition) {
                        holder.userAvatar.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                    }
                });

        holder.username.setText(commentModel.getNickName());
        holder.content.setText(commentModel.getContent());

        if (commentModel.getCreatedAt() != null) {
            holder.createdAt.setText(commentModel.getCreatedAt());
        }

        if (isReply) {
            holder.infoComment.setVisibility(View.GONE);
            holder.wrapComment.setOnClickListener(v -> {
            });
        } else {
            holder.likeComment.setOnClickListener(likeComment);

            if (commentModel.isLiked()) {
                holder.likeComment.setImageResource(R.drawable.liked);
            } else {
                holder.likeComment.setImageResource(R.drawable.like);
            }

            holder.totalLike.setText(String.valueOf(commentModel.getTotalLike()));
            holder.totalReply.setText(String.valueOf(commentModel.getTotalReply()));

            holder.wrapComment.setOnClickListener(replyComment);
        }
    }

    static class EpoxyCommentHolder extends EpoxyHolder {

        public CircleImageView userAvatar;
        public TextView username, createdAt, content, totalLike, totalReply;
        public ImageView likeComment;
        public LinearLayout wrapComment, infoComment;

        @Override
        protected void bindView(@NonNull View itemView) {
            userAvatar = itemView.findViewById(R.id.user_avatar);
            username = itemView.findViewById(R.id.username);
            createdAt = itemView.findViewById(R.id.created_at);
            content = itemView.findViewById(R.id.content);
            likeComment = itemView.findViewById(R.id.like_comment);
            totalLike = itemView.findViewById(R.id.total_like);
            totalReply = itemView.findViewById(R.id.total_reply);
            wrapComment = itemView.findViewById(R.id.wrap_comment);
            infoComment = itemView.findViewById(R.id.info_comment);

        }

    }


    public View.OnClickListener getLikeComment() {
        return likeComment;
    }

    public void setLikeComment(View.OnClickListener likeComment) {
        this.likeComment = likeComment;
    }

    public View.OnClickListener getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(View.OnClickListener replyComment) {
        this.replyComment = replyComment;
    }

}
