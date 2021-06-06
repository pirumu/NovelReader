package com.myproject.novel.ui.novel.detail.rate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.myproject.novel.R;
import com.myproject.novel.local.util.FragmentCallBack;

import java.util.concurrent.atomic.AtomicReference;

import per.wsj.library.AndRatingBar;

public class RateBottomSheetDialog extends BottomSheetDialogFragment {

    public FragmentCallBack fragmentCallBack;

    public RateBottomSheetDialog(FragmentCallBack fcb) {
        this.fragmentCallBack = fcb;
    }

    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_rate, container, false);
        AndRatingBar ratingBar = v.findViewById(R.id.novel_rate);
        ratingBar.setRating(5f);
        TextView feedback = v.findViewById(R.id.feedback);
        String defaultText = getString(R.string.vote_default_txt);
        AtomicReference<Float> currentRate = new AtomicReference<>((float) 1);
        ratingBar.setOnRatingChangeListener((ratingBar1, rating) -> {
            String stringRes = "";
            if (rating <= 2.5f) {

                stringRes = String.format(defaultText, getString(R.string.bad_txt));

            } else if (rating > 2.5f && rating < 4f) {

                stringRes = String.format(defaultText, getString(R.string.medium_txt));
            } else {

                stringRes = String.format(defaultText, getString(R.string.good_txt));
            }

            feedback.setText(stringRes);
            currentRate.set(rating);
        });
        Button sendRate = v.findViewById(R.id.send_rate);


        sendRate.setOnClickListener(cb -> {
            Toast.makeText(getActivity(),
                    getString(R.string.thank_to_vote_txt), Toast.LENGTH_SHORT)
                    .show();
            fragmentCallBack.voteNovel(currentRate.get());
            dismiss();
        });

        return v;
    }

}
