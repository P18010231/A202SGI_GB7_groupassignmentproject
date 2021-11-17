package com.example.a202sgi_groupassignmentproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment {
    private static final String KEY_DATE = "Key_date";
    private static final String EVENT_TIME = "event_time";
    private static final String KEY_START_END = "key_start_end";
    private static final String RETURN_KEY_START_TIME = "return_key_start_time";
    private static final String RETURN_KEY_END_TIME = "return_key_end_time";

    private int mode;
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date date, int i){
        Bundle args = new Bundle();
        args.putSerializable(KEY_DATE, date);
        args.putSerializable(KEY_START_END, i);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(KEY_DATE);
        mode = getArguments().getInt(KEY_START_END);

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        mTimePicker = v.findViewById(R.id.dialog_date_time_picker);
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(min);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        int hour = mTimePicker.getHour();
                        int min = mTimePicker.getMinute();

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hour);
                        c.set(Calendar.MINUTE, min);

                        Bundle result = new Bundle();
                        result.putSerializable(EVENT_TIME, c);

                        if(mode == 1){
                            getParentFragmentManager().setFragmentResult(RETURN_KEY_START_TIME, result);
                        }
                        else if(mode == 2){
                            getParentFragmentManager().setFragmentResult(RETURN_KEY_END_TIME, result);
                        }
                    }
                })
                .setView(v)
                .create();
    }
}
