package com.myproject.novel.ui.novel.detail;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.FragmentCallBack;
import com.myproject.novel.local.util.SharedPreferencesUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.CommentModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.model.ReplyCommentRequestModel;
import com.myproject.novel.ui.main.MainActivity;
import com.myproject.novel.ui.novel.CallbackNovelActivity;
import com.myproject.novel.ui.novel.NovelActivity;
import com.myproject.novel.ui.novel.detail.comment.CommentBottomSheetDialog;
import com.myproject.novel.ui.novel.detail.epoxy.CommentController;
import com.myproject.novel.ui.novel.detail.epoxy.SuggestController;
import com.myproject.novel.ui.novel.detail.rate.RateBottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;

import at.blogc.android.views.ExpandableTextView;
import per.wsj.library.AndRatingBar;

@SuppressWarnings("deprecation")
public class DetailFragment extends Fragment implements SuggestController.EpoxyAdapterCallbacks, CommentController.EpoxyAdapterCallbacks, FragmentCallBack {

    private CallbackNovelActivity mCallback;
    private DetailViewModel mViewModel;
    private View rootView;
    private SuggestController suggestController;
    private CommentController commentController;
    private NovelModel novelModel;
    private String accessToken;
    private CommentBottomSheetDialog commentBottomSheetDialog;

    public static DetailFragment newInstance(NovelModel novelModel) {
        Bundle args = new Bundle();
        Gson gson = new Gson();
        args.putString(UC.NOVEL_MODEL, gson.toJson(novelModel));
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);
        return detailFragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        accessToken = (String) SharedPreferencesUtils.getParam(requireContext(), UC.ACCESS_TOKEN, "");

        rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        CommonUtils.setFullScreenWithStatusBar(requireActivity(), true);
        View decorView = requireActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        Context ctx = requireContext();
        setSuggestController(ctx);
        setCommentController(ctx);
        loadDialogVote();
        Gson gson = new Gson();
        String novelJson = requireArguments().getString(UC.NOVEL_MODEL);
        novelModel = gson.fromJson(novelJson, NovelModel.class);
        addToRecentView(gson, novelJson);
        AndRatingBar novelRate = rootView.findViewById(R.id.novel_rate);
        TextView novelRateText = rootView.findViewById(R.id.novel_rate_text);
        novelRateText.setText(String.valueOf(novelModel.getNovelRate()));
        novelRate.setRating(novelModel.getNovelRate());
        settingExpandableTextView(novelModel.getNovelDescription());
        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int tagId = novelModel.getTag() != null ? novelModel.getTag().getTagId() : 0;
        mViewModel = new ViewModelProvider(this, new DetailViewModelFactory(tagId, novelModel.getNovelId(), accessToken)).get(DetailViewModel.class);
        commentController.setData(novelModel.getComments(), novelModel.getCommentsCount());
        viewModelObserver(tagId);
    }

    private void viewModelObserver(int tagId) {
        this.mViewModel.fetchNovelSuggest(tagId);

        this.mViewModel.listSuggest.observe(getViewLifecycleOwner(), response -> {
            suggestController.setData(response);
        });
    }

    private void settingExpandableTextView(String content) {

        ExpandableTextView expandableTextView = rootView.findViewById(R.id.novel_desc);
        AppCompatButton buttonToggle = rootView.findViewById(R.id.button_toggle);
        expandableTextView.setText(content);
        expandableTextView.setAnimationDuration(750L);
        expandableTextView.setInterpolator(new OvershootInterpolator());
        expandableTextView.setExpandInterpolator(new OvershootInterpolator());
        expandableTextView.setCollapseInterpolator(new OvershootInterpolator());

        buttonToggle.setOnClickListener(v -> {
            if (expandableTextView.isExpanded()) {
                expandableTextView.collapse();
                buttonToggle.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(requireActivity(), R.drawable.down), null, null);

            } else {
                expandableTextView.expand();
                buttonToggle.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(requireActivity(), R.drawable.up), null, null);

            }
        });

        expandableTextView.addOnExpandListener(new ExpandableTextView.OnExpandListener() {
            @Override
            public void onExpand(@Nonnull ExpandableTextView view) {
                Log.d("TAG", "ExpandableTextView expanded");
            }

            @Override
            public void onCollapse(@Nonnull ExpandableTextView view) {
                Log.d("TAG", "ExpandableTextView collapsed");
            }
        });
    }


    private void setSuggestController(Context ctx) {
        suggestController = new SuggestController(this);
        suggestController.setDebugLoggingEnabled(true);
        RecyclerView suggestRecyclerView = rootView.findViewById(R.id.novel_suggest_recycler_view);
        int spanCount = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(ctx, spanCount);
        suggestController.setSpanCount(spanCount);
        layoutManager.setSpanSizeLookup(suggestController.getSpanSizeLookup());
        suggestRecyclerView.setLayoutManager(layoutManager);
        suggestRecyclerView.setAdapter(suggestController.getAdapter());

    }

    private void setCommentController(Context ctx) {
        commentController = new CommentController(this, 1);
        commentController.setDebugLoggingEnabled(true);
        RecyclerView commentRecyclerView = rootView.findViewById(R.id.novel_comment_recycler_view);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        commentRecyclerView.setAdapter(commentController.getAdapter());
    }

    @Override
    public void novelTitleClick(NovelModel model) {
        HashMap<String, String> data = new HashMap<>();
        data.put(UC.NOVEL_ID, String.valueOf(model.getNovelId()));
        CommonUtils.startActivity(requireActivity(), NovelActivity.class, data);

    }

    private void loadDialogVote() {

        Boolean isLogin = (Boolean) SharedPreferencesUtils.getParam(requireContext(), UC.IS_USER_LOGGED_IN, false);

        AppCompatButton openBottomSheet = rootView.findViewById(R.id.edit_vote);
        openBottomSheet.setOnClickListener(
                v -> {
                    if (isLogin) {
                        RateBottomSheetDialog bottomSheet = new RateBottomSheetDialog(this);
                        bottomSheet.show(getParentFragmentManager(), "ModalRateBottomSheet");
                    } else {
                        mCallback.needLogin();
                    }
                });
    }

    @Override
    public void commentClick(CommentModel model) {
        if (commentBottomSheetDialog != null) {
            commentBottomSheetDialog.dismiss();
        }
        commentBottomSheetDialog = null;
        commentBottomSheetDialog = new CommentBottomSheetDialog(true, novelModel, model, this);
        new Handler(Looper.getMainLooper())
                .postDelayed(() -> commentBottomSheetDialog.show(getParentFragmentManager(), "abc"), 500);

    }

    @Override
    public void noCommentClick() {
        if (commentBottomSheetDialog != null) {
            commentBottomSheetDialog.dismiss();
        }
        commentBottomSheetDialog = null;
        commentBottomSheetDialog = new CommentBottomSheetDialog(false, novelModel, this);
        new Handler(Looper.getMainLooper())
                .postDelayed(() -> commentBottomSheetDialog.show(getParentFragmentManager(), "xyz"), 500);
    }


    public void voteNovel(float start) {
        String accessToken = (String) SharedPreferencesUtils.getParam(requireContext(), UC.ACCESS_TOKEN, "");
        mViewModel.rateNovel(accessToken, novelModel.getNovelId(), start);
    }


    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof CallbackNovelActivity) {
            mCallback = (CallbackNovelActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackNovelActivity");
        }
    }


    private void addToRecentView(Gson gson, String novelJson) {

        ArrayList<NovelModel> arrayList = new ArrayList<>();

        Type dataType = new TypeToken<ArrayList<NovelModel>>() {
        }.getType();

        String jsonList = (String) SharedPreferencesUtils.getParam(requireContext(), UC.JUST_WATCHED, "");

        NovelModel novelModel = gson.fromJson(novelJson, NovelModel.class);

        if (!jsonList.equals("")) {
            arrayList = gson.fromJson(jsonList, dataType);
            AtomicBoolean needAdd = new AtomicBoolean(true);
            arrayList.forEach(i -> {
                if (i.getNovelId() == novelModel.getNovelId()) {
                    needAdd.set(false);
                }
            });
            if (needAdd.get()) {
                if (arrayList.size() == 10) {
                    arrayList.remove(0);
                }
                arrayList.add(0, novelModel);
            }

        } else {
            arrayList.add(novelModel);
        }

        SharedPreferencesUtils.setParam(requireContext(), UC.JUST_WATCHED, gson.toJson(arrayList));
    }

    @Override
    public void likeComment(View v, CommentModel model) {

        ImageView likeButton = (ImageView) v;


        if (accessToken.equals("")) {
            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.login_txt))
                    .setMessage(R.string.must_login_txt)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> CommonUtils.startActivity(requireActivity(), MainActivity.class, null))
                    .setNegativeButton(android.R.string.no, (dialog, whichButton) -> dialog.dismiss()).show();
        } else {
            mViewModel.likeCommentUser(accessToken, model.getCommentId());
            if (model.isLiked()) {
                model.setLiked(false);
                likeButton.setImageResource(R.drawable.liked);
                model.setTotalLike(model.getTotalLike() - 1);
            } else {
                model.setLiked(true);
                model.setTotalLike(model.getTotalLike() + 1);
                likeButton.setImageResource(R.drawable.like);
            }

            commentController.setData(novelModel.getComments(), novelModel.getCommentsCount());

        }

    }

    @Override
    public void replyComment(CommentModel model) {

    }

    @Override
    public LiveData<Boolean> commentNovel(int parentId, ReplyCommentRequestModel replyCommentRequestModel) {

        mViewModel.commentUserNovel(accessToken, parentId, replyCommentRequestModel);

        return mViewModel.commentNovel;
    }

    @Override
    public LiveData<List<CommentModel>> getListReply(int parentId) {
        mViewModel.fetchReplyList(accessToken, parentId);
        return mViewModel.replyList;
    }


    @Override
    public void reloadActivity() {
        mCallback.reCreateActivity();
    }
}