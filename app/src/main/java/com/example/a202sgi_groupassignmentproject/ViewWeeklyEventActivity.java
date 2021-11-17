package com.example.a202sgi_groupassignmentproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class ViewWeeklyEventActivity extends AppCompatActivity {
    public static final String EVENT_KEY = "event_key";

    private TextView mName;
    private TextView mDayOfWeek;
    private TextView mTime;
    private TextView mDesc;
    private Button mBtnDelete;
    private EventViewModel viewModel;
    private Event event;

    public static Intent newIntent (Context context, Event event){
        Intent intent = new Intent(context, ViewWeeklyEventActivity.class);
        intent.putExtra(EVENT_KEY, (Serializable) event);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_event);

        event = (Event) getIntent().getSerializableExtra(EVENT_KEY);

        mName = findViewById(R.id.tv_event_name);
        mDayOfWeek = findViewById(R.id.tv_event_day_of_week);
        mTime = findViewById(R.id.tv_event_time);
        mDesc = findViewById(R.id.tv_event_desc);
        mBtnDelete = findViewById(R.id.btn_delete);

        mName.setText(event.getName());
        mDayOfWeek.setText(event.getStringDayOfWeek());
        mTime.setText(event.startToEndToString());
        mDesc.setText(event.getDescription());

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intiViewModel();
                viewModel.deleteEvent(event);
                finish();
            }
        });
    }

    public void intiViewModel(){
        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
    }
}