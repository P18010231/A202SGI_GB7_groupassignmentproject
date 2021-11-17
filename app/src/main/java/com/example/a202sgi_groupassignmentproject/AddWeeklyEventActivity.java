package com.example.a202sgi_groupassignmentproject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

public class AddWeeklyEventActivity extends AppCompatActivity {
    private static final String EVENT_TIME = "event_time";
    private static final String RETURN_KEY_START_TIME = "return_key_start_time";
    private static final String RETURN_KEY_END_TIME = "return_key_end_time";
    private static final String DIALOG_TIME = "dialog_time";

    private EditText mName;
    private Spinner mDayOfWeek;
    private TextView mTime;
    private EditText mDescription;
    private Button mCreateEvent;
    private Event event;
    private EventViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_weekly_event);

        mName = findViewById(R.id.et_event_name);
        mDayOfWeek = findViewById(R.id.sp_event_day_of_week);
        mTime = findViewById(R.id.tv_event_time);
        mDescription = findViewById(R.id.et_event_desc);
        mCreateEvent = findViewById(R.id.btn_add_new_event);

        event = new Event();

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

                FragmentManager manager = getSupportFragmentManager();
                TimePickerFragment dialog1 = TimePickerFragment.newInstance(event.getEndDate(), 2);
                dialog1.show(manager, DIALOG_TIME);
            }
        });

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

                mTime.setText(event.startToEndToString());
            }
        });

        Spinner s = (Spinner) findViewById(R.id.sp_event_day_of_week);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.day_of_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        mDayOfWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        event.setDayOfWeek(1);
                        break;
                    case 1:
                        event.setDayOfWeek(2);
                        break;
                    case 2:
                        event.setDayOfWeek(3);
                        break;
                    case 3:
                        event.setDayOfWeek(4);
                        break;
                    case 4:
                        event.setDayOfWeek(5);
                        break;
                    case 5:
                        event.setDayOfWeek(6);
                        break;
                    case 6:
                        event.setDayOfWeek(7);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                TimePickerFragment dialog1 = TimePickerFragment.newInstance(event.getStartDate(), 1);
                dialog1.show(manager, DIALOG_TIME);
            }
        });

        mCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mName.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddWeeklyEventActivity.this, "Event name cannot be empty", Toast.LENGTH_SHORT).show();

                }else{
                    boolean pass = event.compareDate();

                    if(pass){
                        event.setName(mName.getText().toString());
                        event.setWeekly(true);

                        if(mDescription.getText().toString().isEmpty()){
                            event.setDescription("No description");
                        }
                        event.setDescription(mDescription.getText().toString());

                        intiViewModel();
                        viewModel.insertEvent(event);
                        finish();

                    }
                    else{
                        Toast.makeText(AddWeeklyEventActivity.this, "Invalid date", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void intiViewModel(){
        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
    }
}
