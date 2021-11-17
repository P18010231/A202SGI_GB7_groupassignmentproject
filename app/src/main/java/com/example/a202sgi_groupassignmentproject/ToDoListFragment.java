package com.example.a202sgi_groupassignmentproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ToDoListFragment extends Fragment {
    private RecyclerView mEventRecyclerView;
    private ToDoListAdapter mAdapter;
    private EventViewModel mViewModel;

    FloatingActionButton mFabAddEvent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todolist, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEventRecyclerView = view.findViewById(R.id.todo_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new ToDoListFragment.ToDoListAdapter(getContext());
        mEventRecyclerView.setAdapter(mAdapter);

        mFabAddEvent = view.findViewById(R.id.fabAdd_todo);

        mFabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddToDoList.class);
                startActivity(intent);

            }
        });

        intiViewModel();
        mViewModel.getTodoList();

    }

    public void intiViewModel(){
        mViewModel = new ViewModelProvider(getActivity()).get(EventViewModel.class);

        final Observer<List<Event>> eventListObserver = new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                mAdapter.setEventList(events);
            }
        };

        mViewModel.getTodoList().observe(getActivity(), eventListObserver);
    }

    //holder
    private class ToDoHolder extends RecyclerView.ViewHolder{
        private TextView mTaskTitle;
        private Event mEvent;

        public ToDoHolder(View itemView){
            super(itemView);

            mTaskTitle = itemView.findViewById(R.id.tv_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ViewToDoList.newIntent(getActivity(), mEvent);
                    startActivity(intent);
                }
            });
        }

        public void getEvent(Event event){
            mEvent = event;
        }
    }

    //Adapter
    private class ToDoListAdapter extends RecyclerView.Adapter<ToDoListFragment.ToDoHolder>{
        private Context context;
        private List<Event> todoList;

        public ToDoListAdapter (Context context){
            this.context = context;
        }

        public void setEventList(List<Event> eventList){
            this.todoList = eventList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ToDoListFragment.ToDoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.todo_list, parent, false);
            return new ToDoListFragment.ToDoHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ToDoListFragment.ToDoHolder holder, int position) {
            holder.getEvent(todoList.get(position));
            holder.mTaskTitle.setText(todoList.get(position).getName());
        }

        @Override
        public int getItemCount() {
            if(todoList == null){
                return 0;
            }

            return todoList.size();
        }
    }
}
