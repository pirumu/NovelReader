package com.myproject.novel.ui.novel.detail;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.FragmentCallBack;
import com.myproject.novel.local.util.SharedPreferencesUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.CommentModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.novel.CallbackNovelActivity;
import com.myproject.novel.ui.novel.detail.comment.CommentBottomSheetDialog;
import com.myproject.novel.ui.novel.detail.epoxy.CommentController;
import com.myproject.novel.ui.novel.detail.epoxy.SuggestController;
import com.myproject.novel.ui.novel.detail.rate.RateBottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import per.wsj.library.AndRatingBar;

public class DetailFragment extends Fragment implements SuggestController.EpoxyAdapterCallbacks, CommentController.EpoxyAdapterCallbacks, FragmentCallBack {

    private CallbackNovelActivity mCallback;

    private DetailViewModel mViewModel;
    private View rootView;
    private SuggestController suggestController;
    private CommentController commentController;
    private NovelModel novelModel;

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
        rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        CommonUtils.setFullScreenWithStatusBar(requireActivity(), true);
        Context ctx = requireContext();
        setSuggestController(ctx);
        setCommentController(ctx);
        loadDialogVote();
        Gson gson = new Gson();
        novelModel = gson.fromJson(requireArguments().getString(UC.NOVEL_MODEL), NovelModel.class);
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

        mViewModel = new ViewModelProvider(this, new DetailViewModelFactory(novelModel.getNovelId(), "")).get(DetailViewModel.class);
        suggestController.setData(dumpData());
        commentController.setData(novelModel.getComments());
    }

    private void viewModelObserver() {

        this.mViewModel.novelModel.observe(getViewLifecycleOwner(), response -> {

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
                buttonToggle.setCompoundDrawablesWithIntrinsicBounds(null, requireActivity().getDrawable(R.drawable.down), null, null);

            } else {
                expandableTextView.expand();
                buttonToggle.setCompoundDrawablesWithIntrinsicBounds(null, requireActivity().getDrawable(R.drawable.up), null, null);

            }
        });

        expandableTextView.addOnExpandListener(new ExpandableTextView.OnExpandListener() {
            @Override
            public void onExpand(final ExpandableTextView view) {
                Log.d("TAG", "ExpandableTextView expanded");
            }

            @Override
            public void onCollapse(final ExpandableTextView view) {
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

    }


    private List<NovelModel> dumpData() {

        List<NovelModel> novelModelList = new ArrayList<>();

        novelModelList.add(new NovelModel(1,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Đại Mộng Đương Giác",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/4784170c47.jpg-posterend4"
        ));
        novelModelList.add(new NovelModel(2,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Duẫn",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/3057297ff5.jpg-posterend4"

        ));
        novelModelList.add(new NovelModel(3,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Ôn Thụy An",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/79804526d6.jpg-posterend4"
        ));
        novelModelList.add(new NovelModel(4,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Đại Mộng Đương Giác",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/366260a153.jpg-posterend4"
        ));
        novelModelList.add(new NovelModel(5,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Duẫn",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/7604317d88.jpg"

        ));
        novelModelList.add(new NovelModel(6,
                "Tổng Tài Đại Ác Muốn Cắn Tôi",
                "Ôn Thụy An",
                "http://cn.e.pic.mangatoon.mobi/cartoon-posters/5417029f18.jpg-posterend4"
        ));
        return novelModelList;

    }

    private void loadDialogVote() {

        Boolean isLogin = (Boolean) SharedPreferencesUtils.getParam(requireContext(),UC.IS_USER_LOGGED_IN,false);

            AppCompatButton openBottomSheet = rootView.findViewById(R.id.edit_vote);
            openBottomSheet.setOnClickListener(
                    v -> {
                        if(isLogin) {
                        RateBottomSheetDialog bottomSheet = new RateBottomSheetDialog(this);
                        bottomSheet.show(getParentFragmentManager(), "ModalRateBottomSheet");
                        } else {
                            mCallback.needLogin();
                        }
                    });
    }

    @Override
    public void commentClick(CommentModel model) {
        CommentBottomSheetDialog commentBottomSheetDialog = new CommentBottomSheetDialog(true, novelModel);
        commentBottomSheetDialog.show(getParentFragmentManager(), "ModalCommentBottomSheet");

    }

    @Override
    public void noCommentClick() {
        CommentBottomSheetDialog commentBottomSheetDialog = new CommentBottomSheetDialog(false, novelModel);
        commentBottomSheetDialog.show(getParentFragmentManager(), "ModalCommentBottomSheet");
    }


    public void voteNovel(float start) {
        String accessToken = (String) SharedPreferencesUtils.getParam(requireContext(),UC.ACCESS_TOKEN,"");
        mViewModel.rateNovel(accessToken,novelModel.getNovelId(),start);
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

}