package com.myproject.novel.ui.novel.detail.rate;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
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


import per.wsj.library.AndRatingBar;

public class RateBottomSheetDialog extends BottomSheetDialogFragment {

    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.modal_rate,container, false);
        AndRatingBar ratingBar = (AndRatingBar)v.findViewById(R.id.novel_rate);
        ratingBar.setRating(5f);
        TextView feedback = v.findViewById(R.id.feedback);
        String defaultText = "Đánh giá:(%s)";
        ratingBar.setOnRatingChangeListener((ratingBar1, rating) -> {
            String stringRes = "";
            if(rating <= 2.5f) {

                stringRes = String.format(defaultText,"Tệ");

            } else if(rating > 2.5f && rating < 4f) {

                stringRes = String.format(defaultText,"Trung Bình");
            } else {

                stringRes = String.format(defaultText,"Tốt");
            }

            feedback.setText(stringRes);

        });
        Button sendRate = v.findViewById(R.id.send_rate);


        sendRate.setOnClickListener(v1 -> {
            Toast.makeText(getActivity(),
                    "Cảm ơn bạn đã đánh giá", Toast.LENGTH_SHORT)
                    .show();
            dismiss();
        });

        return v;
    }

}
