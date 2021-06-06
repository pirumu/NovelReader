package com.myproject.novel.ui.novel.chapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myproject.novel.R;
import com.myproject.novel.local.util.CommonUtils;
import com.myproject.novel.local.util.UC;
import com.myproject.novel.model.ChapterModel;
import com.myproject.novel.ui.novel.CallbackNovelActivity;
import com.myproject.novel.ui.novel.ReadActivity;
import com.myproject.novel.ui.novel.chapter.epoxy.ChapterListController;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class ChapterFragment extends Fragment implements ChapterListController.EpoxyAdapterCallbacks {

    private CallbackNovelActivity mCallback;

    private ChapterListController chapterListController;
    private View rootView;
    private TextView currentTextActive;
    private ArrayList<ChapterModel> listChapter;
    private ArrayList<ChapterModel> revertList;

    public static ChapterFragment newInstance(List<ChapterModel> chapterModelList) {
        Bundle args = new Bundle();
        Gson gson = new Gson();
        args.putString(UC.LIST_CHAPTER_MODEL, gson.toJson(chapterModelList));
        ChapterFragment chapterFragment = new ChapterFragment();
        chapterFragment.setArguments(args);
        return chapterFragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chapter_fragment, container, false);
        setController(requireContext());

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Gson gson = new Gson();
        listChapter = gson.fromJson(requireArguments().getString(UC.LIST_CHAPTER_MODEL), new TypeToken<List<ChapterModel>>() {
        }.getType());
        revertList = CommonUtils.reverseArrayList(listChapter);
        chapterListController.setData(listChapter);
    }

    @Override
    public void chapterTitleClick(ChapterModel model) {
        HashMap<String, String> data = new HashMap<>();
        data.put(UC.CHAPTER_ID, String.valueOf(model.getChapterId()));
        data.put(UC.NOVEL_ID, String.valueOf(model.getNovelId()));
        CommonUtils.startActivity(requireActivity(), ReadActivity.class, data);
    }

    @Override
    public void sortNewClick() {
        currentTextActive = rootView.findViewById(R.id.sort_new);
        currentTextActive.setTextColor(Color.parseColor(getString(R.string.popular_color)));
        currentTextActive = rootView.findViewById(R.id.sort_old);
        currentTextActive.setTextColor(Color.parseColor(getString(R.string.sort_defaut_color)));
        chapterListController.setData(revertList);
    }

    @Override
    public void sortOldClick() {
        currentTextActive = rootView.findViewById(R.id.sort_old);
        currentTextActive.setTextColor(Color.parseColor(getString(R.string.popular_color)));
        currentTextActive = rootView.findViewById(R.id.sort_new);
        currentTextActive.setTextColor(Color.parseColor(getString(R.string.sort_defaut_color)));
        chapterListController.setData(listChapter);
    }

    private void setController(Context ctx) {
        chapterListController = new ChapterListController(this);
        RecyclerView listChapterRecyclerView = rootView.findViewById(R.id.list_chapter);
//        listChapterRecyclerView.setHasFixedSize(true);
        listChapterRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        listChapterRecyclerView.setAdapter(chapterListController.getAdapter());
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