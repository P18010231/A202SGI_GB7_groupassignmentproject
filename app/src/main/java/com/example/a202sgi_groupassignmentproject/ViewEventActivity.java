package com.example.a202sgi_groupassignmentproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import java.io.Serializable;

public class ViewEventActivity extends AppCompatActivity {
    public static final String EVENT_KEY = "event_key";

    private TextView mName;
    private TextView mStartTime;
    private TextView mEndTime;
    private TextView mDesc;
    private Button mBtnDelete;
    private EventViewModel viewModel;
    private Event event;

    public static Intent newIntent (Context context, Event event){
        Intent intent = new Intent(context, ViewEventActivity.class);
        intent.putExtra(EVENT_KEY, (Serializable) event);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);

        event = (Event) getIntent().getSerializableExtra(EVENT_KEY);

        mName = findViewById(R.id.tv_event_name);
        mStartTime = findViewById(R.id.tv_event_start_time);
        mEndTime = findViewById(R.id.tv_event_end_time);
        mDesc = findViewById(R.id.tv_event_desc);
        mBtnDelete = findViewById(R.id.btn_delete);

        mName.setText(event.getName());
        mStartTime.setText(event.startDateToString());
        mEndTime.setText(event.endDateToString());
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
