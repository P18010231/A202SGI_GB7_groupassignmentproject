package com.example.a202sgi_groupassignmentproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity {

    private static final String DIALOG_DATE = "dialog_date";
    private static final String DIALOG_TIME = "dialog_time";
    private static final String EVENT_DATE = "event_date";
    private static final String EVENT_TIME = "event_time";
    private static final String RETURN_KEY_START_DATE = "return_key_start_date";
    private static final String RETURN_KEY_END_DATE = "return_key_end_date";
    private static final String RETURN_KEY_START_TIME = "return_key_start_time";
    private static final String RETURN_KEY_END_TIME = "return_key_end_time";

    private EditText mName;
    private TextView mStartTime;
    private TextView mEndTime;
    private EditText mDescription;
    private Button mCreateEvent;
    private Event event;
    private EventViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        mName = findViewById(R.id.et_event_name);
        mStartTime = findViewById(R.id.et_event_start_time);
        mEndTime = findViewById(R.id.et_event_end_time);
        mDescription = findViewById(R.id.et_event_desc);
        mCreateEvent = findViewById(R.id.btn_add_new_event);

        event = new Event();

        //after start date is picked
        getSupportFragmentManager().setFragmentResultListener(RETURN_KEY_START_DATE, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Date startDate = (Date) result.getSerializable(EVENT_DATE);
                event.setStartDate(startDate);

                FragmentManager manager = getSupportFragmentManager();
                TimePickerFragment dialog1 = TimePickerFragment.newInstance(event.getStartDate(), 1);
                dialog1.show(manager, DIALOG_TIME);
            }
        });

        //after start time is picked
        getSupportFragmentManager().setFragmentResultListener(RETURN_KEY_START_TIME, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Calendar startTime = (Calendar) result.getSerializable(EVENT_TIME);
                Date eventDate = event.getStartDate();
                Calendar c = Calendar.getInstance();
                c.setTime(eventDate);
                c.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
                c.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));

                event.setStartDate(c.getTime());
                mStartTime.setText(event.startDateToString());
            }
        });

        //after end date is picked
        getSupportFragmentManager().setFragmentResultListener(RETURN_KEY_END_DATE, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Date endDate = (Date) result.getSerializable(EVENT_DATE);
                event.setEndDate(endDate);

                FragmentManager manager = getSupportFragmentManager();
                TimePickerFragment dialog1 = TimePickerFragment.newInstance(event.getEndDate(), 2);
                dialog1.show(manager, DIALOG_TIME);
            }
        });

        //after end time is picked
        getSupportFragmentManager().setFragmentResultListener(RETURN_KEY_END_TIME, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Calendar endTime = (Calendar) result.getSerializable(EVENT_TIME);
                Date eventDate = event.getEndDate();
                Calendar c = Calendar.getInstance();
                c.setTime(eventDate);
                c.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
                c.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));

                event.setEndDate(c.getTime());
                mEndTime.setText(event.endDateToString());
            }
        });


        //when the start time field is clicked
        mStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(event.getStartDate(), 1);
                dialog.show(manager, DIALOG_DATE);


            }
        });

        //when the end time filed is clicked
        mEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(event.getEndDate(), 2);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        //when the create event button is clicked
        mCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mName.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddEventActivity.this, "Event name cannot be empty", Toast.LENGTH_SHORT).show();

                }else{
                    boolean pass = event.compareDate();

                    if(pass){
                        event.setName(mName.getText().toString());
                        event.setWeekly(false);
                        event.setDayOfWeek(0);

                        if(mDescription.getText().toString().isEmpty()){
                            event.setDescription("No description");
                        }
                        event.setDescription(mDescription.getText().toString());

                        intiViewModel();
                        viewModel.insertEvent(event);
                        finish();

                    }
                    else{
                        Toast.makeText(AddEventActivity.this, "Invalid date", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void intiViewModel(){
        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
    }
}
