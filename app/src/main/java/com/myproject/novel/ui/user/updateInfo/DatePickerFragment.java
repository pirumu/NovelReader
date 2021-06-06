package com.myproject.novel.ui.user.updateInfo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.myproject.novel.local.util.customfonts.EditText_Poppins_Regular;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private final EditText_Poppins_Regular birthdayInput;
    private final String currentTime;

    public DatePickerFragment(EditText_Poppins_Regular bdi, String crt) {
        this.birthdayInput = bdi;
        this.currentTime = crt;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        if (this.currentTime == null || this.currentTime.equals("")) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        } else {
            String[] date = this.currentTime.split("/");

            int year = Integer.parseInt(date[2]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[0]);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.birthdayInput.setText(day + "/" + month + "/" + year);
    }
}
