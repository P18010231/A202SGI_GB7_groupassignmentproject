package com.example.a202sgi_groupassignmentproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class AddToDoList extends AppCompatActivity {

    private EditText mName;
    private EditText mDescription;
    private Button mCreateEvent;
    private Event event;
    private EventViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todolist);

        mName = findViewById(R.id.et_task_name);
        mDescription = findViewById(R.id.et_task_desc);
        mCreateEvent = findViewById(R.id.btn_add_new_task);

        event = new Event();

        mCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mName.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddToDoList.this, "Event name cannot be empty", Toast.LENGTH_SHORT).show();

                }else{
                    event.setName(mName.getText().toString());
                    event.setWeekly(false);
                    event.setTodo(true);
                    event.setDayOfWeek(0);

                    if(mDescription.getText().toString().isEmpty()){
                        event.setDescription("No description");
                    }
                    event.setDescription(mDescription.getText().toString());

                    intiViewModel();
                    viewModel.insertEvent(event);
                    finish();
                }
            }
        });
    }
    public void intiViewModel(){
        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
    }
}
