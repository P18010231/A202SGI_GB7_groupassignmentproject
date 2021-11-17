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

public class ViewToDoList extends AppCompatActivity {
    public static final String EVENT_KEY = "event_key";

    private TextView mName;
    private TextView mDesc;
    private Button mBtnDelete;
    private EventViewModel viewModel;
    private Event event;

    public static Intent newIntent (Context context, Event event){
        Intent intent = new Intent(context, ViewToDoList.class);
        intent.putExtra(EVENT_KEY, (Serializable) event);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo);

        event = (Event) getIntent().getSerializableExtra(EVENT_KEY);

        mName = findViewById(R.id.tv_task_name);
        mDesc = findViewById(R.id.tv_task_desc);
        mBtnDelete = findViewById(R.id.btn_delete);

        mName.setText(event.getName());
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
