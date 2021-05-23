package com.myproject.novel.ui.novel.detail;

import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;


import at.blogc.android.views.ExpandableTextView;
import com.myproject.novel.R;
import com.myproject.novel.model.CommentModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.novel.detail.comment.CommentBottomSheetDialog;
import com.myproject.novel.ui.novel.detail.epoxy.CommentController;
import com.myproject.novel.ui.novel.detail.epoxy.SuggestController;
import com.myproject.novel.ui.novel.detail.rate.RateBottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import per.wsj.library.AndRatingBar;

public class DetailFragment extends Fragment implements SuggestController.EpoxyAdapterCallbacks, CommentController.EpoxyAdapterCallbacks {

    private AndRatingBar novelRate;
    private DetailViewModel mViewModel;
    private View rootView;
    private SuggestController suggestController;
    private CommentController commentController;


    public static DetailFragment newInstance() {
        return new DetailFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        novelRate = rootView.findViewById(R.id.novel_rate);
        novelRate.setRating(5f);
        Context ctx = requireContext();
        settingExpandableTextView();
        setSuggestController(ctx);
        setCommentController(ctx);
        loadDialogVote();
        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        suggestController.setData(dumpData());
        commentController.setData(dumpComment());
    }

    private void settingExpandableTextView() {

         ExpandableTextView expandableTextView = (ExpandableTextView) rootView.findViewById(R.id.novel_desc);
        AppCompatButton buttonToggle = (AppCompatButton) rootView.findViewById(R.id.button_toggle);

        expandableTextView.setAnimationDuration(750L);
        expandableTextView.setInterpolator(new OvershootInterpolator());
        expandableTextView.setExpandInterpolator(new OvershootInterpolator());
        expandableTextView.setCollapseInterpolator(new OvershootInterpolator());

//        buttonToggle.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(final View v)
//            {
//                buttonToggle.setText(expandableTextView.isExpanded() ? "Mo": "Dong");
//                expandableTextView.toggle();
//            }
//        });

        buttonToggle.setOnClickListener(v -> {
            if (expandableTextView.isExpanded())
            {
                expandableTextView.collapse();
                buttonToggle.setCompoundDrawablesWithIntrinsicBounds(null,requireActivity().getDrawable(R.drawable.down),null,null);

            }
            else
            {
                expandableTextView.expand();
                buttonToggle.setCompoundDrawablesWithIntrinsicBounds(null,requireActivity().getDrawable(R.drawable.up),null,null);

            }
        });

        expandableTextView.addOnExpandListener(new ExpandableTextView.OnExpandListener()
        {
            @Override
            public void onExpand(final ExpandableTextView view)
            {
                Log.d("TAG", "ExpandableTextView expanded");
            }

            @Override
            public void onCollapse(final ExpandableTextView view)
            {
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
        commentController = new CommentController(this);
        commentController.setDebugLoggingEnabled(true);
        RecyclerView commentRecyclerView = rootView.findViewById(R.id.novel_comment_recycler_view);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        commentRecyclerView.setAdapter(commentController.getAdapter());
    }

    @Override
    public void novelTitleClick(NovelModel model) {

    }


    private  List<NovelModel> dumpData() {

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

    private  List<CommentModel> dumpComment() {

        List<CommentModel> novelModelList = new ArrayList<>();

        novelModelList.add(new CommentModel(1,
                "Truyện hay tuyệt vời",
                "28/11/2021"
        ));
        novelModelList.add(new CommentModel(2,
                "Sợ hãi ông Vũ, mất trí nhớ mất luôn cái gọi là liêm sỉ, sĩ diện của một thiếu gia của Nam Cung gia, tính tàn ác cũng theo trí nhớ bay đi",
                "28/11/2021"

        ));
        novelModelList.add(new CommentModel(3,
                "bả cũng mê trai lắm đấy=)))",
                "28/11/2021"
        ));
        novelModelList.add(new CommentModel(4,
                "từ bạn trở thành trà xanh r",
                "28/11/2021"
        ));
        novelModelList.add(new CommentModel(5,
                "về sau ông mà bỏ rơi c con dân ném đá u đầu ông ra",
                "28/11/2021"

        ));

        return novelModelList;

    }

    private void loadDialogVote(){
        AppCompatButton openBottomSheet = rootView.findViewById(R.id.edit_vote);
        openBottomSheet.setOnClickListener(
                v -> {
                    RateBottomSheetDialog bottomSheet = new RateBottomSheetDialog();
                    bottomSheet.show(getParentFragmentManager(),"ModalRateBottomSheet");
                });
    }
    @Override
    public void commentClick(CommentModel model) {

        Toast.makeText(getContext(), "Hello toast!", Toast.LENGTH_SHORT).show();

        CommentBottomSheetDialog commentBottomSheetDialog = new CommentBottomSheetDialog();
        commentBottomSheetDialog.show(getParentFragmentManager(),"ModalCommentBottomSheet");

    }
}