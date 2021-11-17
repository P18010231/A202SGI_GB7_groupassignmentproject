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

public class WeeklyEventFragment extends Fragment {
    private RecyclerView mEventRecyclerView;
    private WeeklyEventAdapter mAdapter;
    private EventViewModel mViewModel;

    FloatingActionButton mFabAddEvent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weekly_event, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEventRecyclerView = view.findViewById(R.id.weekly_event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new WeeklyEventAdapter(getContext());
        mEventRecyclerView.setAdapter(mAdapter);

        mFabAddEvent = view.findViewById(R.id.fabAdd_weekly);

        mFabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddWeeklyEventActivity.class);
                startActivity(intent);

            }
        });

        intiViewModel();
        mViewModel.getWeeklyEventList();

    }

    public void intiViewModel(){
        mViewModel = new ViewModelProvider(getActivity()).get(EventViewModel.class);

        final Observer<List<Event>> eventListObserver = new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                mAdapter.setEventList(events);
            }
        };

        mViewModel.getWeeklyEventList().observe(getActivity(), eventListObserver);
    }

    //holder
    private class WeeklyEventHolder extends RecyclerView.ViewHolder{
        private TextView mEventTitle;
        private TextView mDayOfWeek;
        private TextView mTime;
        private Event mEvent;

        public WeeklyEventHolder(View itemView){
            super(itemView);

            mEventTitle = itemView.findViewById(R.id.tv_title);
            mDayOfWeek = itemView.findViewById(R.id.tv_day_of_week);
            mTime = itemView.findViewById(R.id.tv_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ViewWeeklyEventActivity.newIntent(getActivity(), mEvent);
                    startActivity(intent);
                }
            });
        }

        public void getEvent(Event event){
            mEvent = event;
        }
    }

    //need to change here
    //Adapter
    private class WeeklyEventAdapter extends RecyclerView.Adapter<WeeklyEventHolder>{
        private Context context;
        private List<Event> eventList;
        private HandlerEventClick clickListener;

        public WeeklyEventAdapter (Context context){
            this.context = context;
            //.clickListener = clickListener;
        }

        public void setEventList(List<Event> eventList){
            this.eventList = eventList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public WeeklyEventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.weekly_event_list, parent, false);
            return new WeeklyEventHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WeeklyEventHolder holder, int position) {
            holder.getEvent(eventList.get(position));
            holder.mEventTitle.setText(eventList.get(position).getName());
            holder.mDayOfWeek.setText(eventList.get(position).getStringDayOfWeek());
            holder.mTime.setText(eventList.get(position).startToEndToString());
        }

        @Override
        public int getItemCount() {
            if(eventList == null){
                return 0;
            }

            return eventList.size();
        }
    }

    //interface
    public interface HandlerEventClick{
        void removeWord(Event event);
        void editWord(Event event);
    }
}
