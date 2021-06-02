package com.myproject.novel.ui.novel;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.myproject.novel.model.ChapterModel;
import com.myproject.novel.model.NovelModel;
import com.myproject.novel.ui.novel.chapter.ChapterFragment;
import com.myproject.novel.ui.novel.detail.DetailFragment;

import java.util.ArrayList;
import java.util.List;


public class NovelAdapter extends FragmentStateAdapter {
    private final ArrayList<Fragment> fragments;
    private final ChapterFragment chapterFragment;
    private final DetailFragment detailFragment;

    public NovelAdapter(@NonNull FragmentActivity fragmentActivity, NovelModel novelModel, List<ChapterModel> chapterModelList) {
        super(fragmentActivity);
        this.fragments = new ArrayList<>();
        chapterFragment = ChapterFragment.newInstance(chapterModelList);
        detailFragment = DetailFragment.newInstance(novelModel);
        fragments.add(detailFragment);
        fragments.add(chapterFragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return detailFragment;
        }
        return chapterFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public ArrayList<Fragment> getFragments() {
        return fragments;
    }
}
