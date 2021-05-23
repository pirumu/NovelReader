package com.myproject.novel.ui.novel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.myproject.novel.ui.novel.chapter.ChapterFragment;
import com.myproject.novel.ui.novel.detail.DetailFragment;

import org.jetbrains.annotations.NotNull;


public class NovelAdapter extends FragmentStateAdapter {

    public NovelAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) {
            return  DetailFragment.newInstance();
        }
        return  ChapterFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
