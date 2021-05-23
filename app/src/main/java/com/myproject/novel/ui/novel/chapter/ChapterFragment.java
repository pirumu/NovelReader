package com.myproject.novel.ui.novel.chapter;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myproject.novel.R;
import com.myproject.novel.model.ChapterModel;
import com.myproject.novel.ui.novel.chapter.epoxy.ChapterListController;

import java.util.ArrayList;
import java.util.List;

public class ChapterFragment extends Fragment implements ChapterListController.EpoxyAdapterCallbacks {

    private ChapterListController chapterListController;
    private ChapterViewModel mViewModel;
    private View rootView;
    private int activeId = R.id.sort_new;
    private TextView currentTextActive;

    public static ChapterFragment newInstance() {
        return new ChapterFragment();
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
        mViewModel = new ViewModelProvider(this).get(ChapterViewModel.class);
        chapterListController.setData(dump());

    }

    @Override
    public void chapterTitleClick(ChapterModel model) {

    }

    @Override
    public void sortClick(ChapterModel model) {
        if(activeId == R.id.sort_new) {
            currentTextActive = rootView.findViewById(activeId);
            currentTextActive.setTextColor(Color.parseColor("#8A8383"));
            currentTextActive = rootView.findViewById(R.id.sort_old);
            currentTextActive.setTextColor(Color.parseColor("#4896f0"));
            activeId = R.id.sort_old;
        } else {
            currentTextActive = rootView.findViewById(R.id.sort_old);
            currentTextActive.setTextColor(Color.parseColor("#8A8383"));
            currentTextActive= rootView.findViewById(R.id.sort_new);
            currentTextActive.setTextColor(Color.parseColor("#4896f0"));
            activeId = R.id.sort_new;
        }
    }


    private void setController(Context ctx) {
        chapterListController = new ChapterListController(this);
        RecyclerView listChapterRecyclerView = rootView.findViewById(R.id.list_chapter);
        listChapterRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        listChapterRecyclerView.setAdapter(chapterListController.getAdapter());
    }

    private List<ChapterModel> dump() {
        List<ChapterModel> dump = new ArrayList<>();
        dump.add(new ChapterModel(1,"Chương 1: Ai là vợ của anh?","ABCXYZ"));
        dump.add(new ChapterModel(2,"Chương 2: Nhặt được một cục nợ","ABCXYZ"));
        dump.add(new ChapterModel(3,"Chương 3: Ai là vợ của anh?","ABCXYZ"));
        dump.add(new ChapterModel(4,"Chương 4: Ai là vợ của anh?","ABCXYZ"));
        dump.add(new ChapterModel(5,"Chương 5: Ai là vợ của anh?","ABCXYZ"));
        dump.add(new ChapterModel(6,"Chương 6: Ai là vợ của anh?","ABCXYZ"));
        dump.add(new ChapterModel(7,"Chương 7: Ai là vợ của anh?","ABCXYZ"));
        dump.add(new ChapterModel(8,"Chương 8: Ai là vợ của anh?","ABCXYZ"));
        dump.add(new ChapterModel(9,"Chương 1: Ai là vợ của anh?","ABCXYZ"));
        dump.add(new ChapterModel(10,"Chương 2: Nhặt được một cục nợ","ABCXYZ"));
        dump.add(new ChapterModel(11,"Chương 3: Ai là vợ của anh?","ABCXYZ"));
        dump.add(new ChapterModel(12,"Chương 4: Ai là vợ của anh?","ABCXYZ"));
        dump.add(new ChapterModel(13,"Chương 5: Ai là vợ của anh?","ABCXYZ"));
        dump.add(new ChapterModel(14,"Chương 6: Ai là vợ của anh?","ABCXYZ"));
        dump.add(new ChapterModel(15,"Chương 7: Ai là vợ của anh?","ABCXYZ"));
        dump.add(new ChapterModel(16,"Chương 8: Ai là vợ của anh?","ABCXYZ"));
        return dump;
    }



}