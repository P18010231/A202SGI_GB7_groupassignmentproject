package com.example.a202sgi_groupassignmentproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.CharArrayReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment{
    private static final String KEY_DATE = "Key_date";
    private static final String EVENT_DATE = "event_date";
    private static final String KEY_START_END = "key_start_end";
    private static final String RETURN_KEY_START_DATE = "return_key_start_date";
    private static final String RETURN_KEY_END_DATE = "return_key_end_date";

    private int mode;
    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date, int i){
        Bundle args = new Bundle();
        args.putSerializable(KEY_DATE, date);
        args.putSerializable(KEY_START_END, i);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(KEY_DATE);
        mode = getArguments().getInt(KEY_START_END);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        mDatePicker = v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        int day = mDatePicker.getDayOfMonth();
                        int month = mDatePicker.getMonth();
                        int year = mDatePicker.getYear();

                        Date d = new GregorianCalendar(year, month, day).getTime();

                        Bundle result = new Bundle();
                        result.putSerializable(EVENT_DATE, d);

                        if(mode == 1){
                            getParentFragmentManager().setFragmentResult(RETURN_KEY_START_DATE, result);
                        }
                        else if(mode == 2){
                            getParentFragmentManager().setFragmentResult(RETURN_KEY_END_DATE, result);
                        }

                    }
                })
                .setView(v)
                .create();

    }

}
