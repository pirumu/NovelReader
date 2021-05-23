package com.myproject.novel.ui.novel.detail.epoxy;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.myproject.novel.R;
import com.myproject.novel.model.CommentModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.model.UserModel;
import com.myproject.novel.ui.home.epoxy.EpoxyNovelModel;

import de.hdodenhof.circleimageview.CircleImageView;

@EpoxyModelClass
public abstract class EpoxyCommentModel  extends EpoxyModelWithHolder<EpoxyCommentModel.EpoxyCommentHolder> {


    @Override
    protected int getDefaultLayout() {
        return R.layout.comment_item;
    }

    @EpoxyAttribute
    public CommentModel commentModel;
    @EpoxyAttribute
    public UserModel userModel;

    @EpoxyAttribute
    public View.OnClickListener clickListener;

    public EpoxyCommentModel( UserModel userModel, CommentModel commentModel,View.OnClickListener clickListener ) {
        this.clickListener = clickListener;
        this.commentModel = commentModel;
        this.userModel = userModel;
    }


    @Override
    public void bind(@NonNull EpoxyCommentHolder holder) {
        super.bind(holder);
        Glide.with(holder.userAvatar.getContext()).load(userModel.avatar).transition(
                DrawableTransitionOptions.withCrossFade()).into(holder.userAvatar);
        holder.username.setText(userModel.username);
        holder.content.setText(commentModel.content);
        holder.createdAt.setText(commentModel.createdAt);

    }

    static class EpoxyCommentHolder extends EpoxyHolder {

        public CircleImageView userAvatar;
        public TextView username,createdAt,content;
        @Override
        protected void bindView(@NonNull View itemView) {
            userAvatar = itemView.findViewById(R.id.user_avatar);
            username = (TextView)itemView.findViewById(R.id.username);
            createdAt = (TextView)itemView.findViewById(R.id.created_at);
            content = (TextView)itemView.findViewById(R.id.content);
        }

    }

}
