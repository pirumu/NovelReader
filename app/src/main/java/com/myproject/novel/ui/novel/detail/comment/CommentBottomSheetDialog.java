package com.myproject.novel.ui.novel.detail.comment;

import android.app.Service;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.FragmentCallBack;
import com.myproject.novel.local.util.GlideApp;
import com.myproject.novel.local.util.SharedPreferencesUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.CommentModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.model.ReplyCommentRequestModel;
import com.myproject.novel.ui.novel.detail.comment.epoxy.CommentModalController;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;


public class CommentBottomSheetDialog extends BottomSheetDialogFragment implements OnKeyboardVisibilityListener, CommentModalController.EpoxyAdapterCallbacks {

    private View rootView;
    private EditText editText;
    private final NovelModel novelModel;


    private boolean isReply;
    private final CommentModel parentComment;
    private ImageView sendCommentBtn;
    public FragmentCallBack fragmentCallBack;
    private LiveData<Boolean> resultReply;
    private CommentModalController commentModalController;
    private ProgressBar spinner;
    private String accessToken;
    private InputMethodManager imm;
    RecyclerView listComment;

    public CommentBottomSheetDialog(boolean isReply, NovelModel novelModel, FragmentCallBack fc) {
        this.novelModel = novelModel;
        this.isReply = isReply;
        parentComment = new CommentModel();
        fragmentCallBack = fc;
    }

    public CommentBottomSheetDialog(boolean isReply, NovelModel novelModel, CommentModel commentModel, FragmentCallBack fc) {
        this.novelModel = novelModel;
        this.isReply = isReply;
        this.parentComment = commentModel;
        fragmentCallBack = fc;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }


    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();

        if (dialog != null) {
            dialog.setOnShowListener(dialog1 -> {
                FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                if (bottomSheet != null) {
                    BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    behavior.setDraggable(false);
                    behavior.setPeekHeight(0);
                    bottomSheet.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                    ViewCompat.setBackground(bottomSheet, getDrawable(requireContext(), R.drawable.rounded_dialog));
                }
            });
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CommonUtils.enableLightStatusBar(requireActivity());
        rootView = inflater.inflate(R.layout.modal_comment_full, container, false);
        setKeyboardVisibilityListener(this);
        accessToken = (String) SharedPreferencesUtils.getParam(requireContext(), UC.ACCESS_TOKEN, "");
        ImageButton backButton = rootView.findViewById(R.id.back_btn);
        backButton.setOnClickListener(v -> {
            dismiss();
        });

        TextView nameToolbar = rootView.findViewById(R.id.name_toolbar);
        nameToolbar.setText(this.novelModel.getNovelTitle());

        if (accessToken.equals("")) {
            LinearLayout footerView = rootView.findViewById(R.id.footer);
            footerView.setVisibility(View.INVISIBLE);
        }
        // important
        editText = rootView.findViewById(R.id.structured_edittext_answer);
        imm = (InputMethodManager) requireActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
        sendCommentBtn = rootView.findViewById(R.id.send_comment);

        LinearLayout replyComment = rootView.findViewById(R.id.current_comment);
        if (isReply) {
            replyComment.setVisibility(View.VISIBLE);
            CircleImageView avatar = replyComment.findViewById(R.id.user_avatar);
            TextView username = replyComment.findViewById(R.id.username);
            TextView content = replyComment.findViewById(R.id.content);
            TextView createdAt = replyComment.findViewById(R.id.created_at);
            TextView totalLike = replyComment.findViewById(R.id.total_like);
            TextView totalReply = replyComment.findViewById(R.id.total_reply);
            totalReply.setVisibility(View.INVISIBLE);
            ImageView likeAction = replyComment.findViewById(R.id.like_action);
            username.setText(parentComment.getNickName());
            content.setText(parentComment.getContent());
            totalLike.setText(String.valueOf(parentComment.getTotalLike()));
            totalReply.setText(String.valueOf(parentComment.getTotalReply()));

            if (parentComment.getCreatedAt() != null) {
                createdAt.setText(parentComment.getCreatedAt());
            }

            if (parentComment.isLiked()) {
                likeAction.setImageResource(R.drawable.liked);
            } else {
                likeAction.setImageResource(R.drawable.like);
            }

            GlideApp.with(requireContext()).asBitmap().load(parentComment.getAvatar())
                    .placeholder(CommonUtils.shimmerEffect())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, Transition<? super Bitmap> transition) {
                            avatar.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onLoadCleared(Drawable placeholder) {
                        }
                    });
        } else {
            replyComment.setVisibility(View.GONE);
        }
        spinner = rootView.findViewById(R.id.loading_content);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#4896f0"), android.graphics.PorterDuff.Mode.MULTIPLY);
        spinner.setVisibility(View.GONE);

        settingAdapter(isReply);
        settingSendComment(isReply);

        return rootView;
    }


    @Override
    public void onVisibilityChanged(boolean visible) {
        if (visible) {
            editText.setLines(5);
        } else {
            editText.setLines(1);
            Log.i("Keyboard state", "Ignoring global layout change...");
        }
    }

    private void settingAdapter(boolean isReply) {

        commentModalController = new CommentModalController(this);
        listComment = rootView.findViewById(R.id.list_comment_modal);
        listComment.setVisibility(View.INVISIBLE);
        listComment.setLayoutManager(new LinearLayoutManager(requireContext()));
        listComment.setAdapter(commentModalController.getAdapter());
        spinner.setVisibility(View.VISIBLE);

        if (isReply) {
            commentModalController.setData(new ArrayList<>(), true);
            fragmentCallBack.getListReply(parentComment.getCommentId()).observe(getViewLifecycleOwner(), list -> {
                spinner.setVisibility(View.GONE);
                listComment.setVisibility(View.VISIBLE);
                commentModalController.setData(list, true);
            });
        } else {
            commentModalController.setData(new ArrayList<>(), false);
            fragmentCallBack.getListReply(-1).observe(getViewLifecycleOwner(), list -> {
                spinner.setVisibility(View.GONE);
                listComment.setVisibility(View.VISIBLE);
                commentModalController.setData(list, false);
            });
        }

    }

    private void settingSendComment(boolean isReply) {
        if (isReply) {
            sendCommentBtn.setOnClickListener(v -> replyComment());

        } else {
            sendCommentBtn.setOnClickListener(v -> newComment());
        }
    }

    private void newComment() {
        {

            if (!editText.getText().toString().equals("")) {
                spinner.setVisibility(View.VISIBLE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                ReplyCommentRequestModel replyCommentRequestModel = new ReplyCommentRequestModel(editText.getText().toString(), novelModel.getNovelId());
                resultReply = fragmentCallBack.commentNovel(0, replyCommentRequestModel);

                resultReply.observe(getViewLifecycleOwner(), res -> {
                    editText.setText("");
                    editText.clearFocus();
                    fragmentCallBack.reloadActivity();
                    if (res) {
                        fragmentCallBack.getListReply(-1).observe(getViewLifecycleOwner(), list -> {
                            spinner.setVisibility(View.GONE);
                            commentModalController.setData(list, true);
                        });
                    }

                });
            }
        }
    }

    private void replyComment() {
        {

            if (!editText.getText().toString().equals("")) {
                spinner.setVisibility(View.VISIBLE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                ReplyCommentRequestModel replyCommentRequestModel = new ReplyCommentRequestModel(editText.getText().toString());
                resultReply = fragmentCallBack.commentNovel(parentComment.getCommentId(), replyCommentRequestModel);

                resultReply.observe(getViewLifecycleOwner(), res -> {
                    editText.setText("");
                    editText.clearFocus();
                    if (res) {
                        fragmentCallBack.reloadActivity();
                        fragmentCallBack.getListReply(parentComment.getCommentId()).observe(getViewLifecycleOwner(), list -> {
                            spinner.setVisibility(View.GONE);
                            commentModalController.setData(list, false);
                        });
                    }

                });
            }
        }
    }

    private void setKeyboardVisibilityListener(final OnKeyboardVisibilityListener onKeyboardVisibilityListener) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private boolean alreadyOpen;
            private final Rect rect = new Rect();

            @Override
            public void onGlobalLayout() {
                int defaultKeyboardHeightDP = 100;
                int estimatedKeyboardDP = defaultKeyboardHeightDP + 48;
                int estimatedKeyboardHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, estimatedKeyboardDP, rootView.getResources().getDisplayMetrics());
                rootView.getWindowVisibleDisplayFrame(rect);
                int heightDiff = rootView.getRootView().getHeight() - (rect.bottom - rect.top);
                boolean isShown = heightDiff >= estimatedKeyboardHeight;

                if (isShown == alreadyOpen) {
                    rootView.setPadding(0, 0, 0, heightDiff);
                    return;
                } else {
                    if (rootView.getPaddingBottom() != 0) {
                        rootView.setPadding(0, 0, 0, 0);
                    }
                    alreadyOpen = isShown;
                }
                onKeyboardVisibilityListener.onVisibilityChanged(isShown);
            }
        });
    }

    @Override
    public void commentClick(CommentModel model) {
        fragmentCallBack.commentClick(model);
    }

    @Override
    public void replyComment(CommentModel model) {

    }

    @Override
    public void likeComment(View v, CommentModel model) {
        fragmentCallBack.likeComment(v, model);
    }

    @Override
    public void noCommentClick() {

    }

    @Override
    public void onStart() {
        super.onStart();
        listComment.setVisibility(View.INVISIBLE);
        listComment.removeAllViewsInLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        listComment.setVisibility(View.INVISIBLE);
        listComment.removeAllViewsInLayout();
    }

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        listComment.setVisibility(View.INVISIBLE);
        listComment.removeAllViewsInLayout();
        super.onDismiss(dialog);
    }

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }


}
