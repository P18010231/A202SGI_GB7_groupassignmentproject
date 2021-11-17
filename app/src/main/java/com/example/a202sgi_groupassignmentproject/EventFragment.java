package com.example.a202sgi_groupassignmentproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.List;

public class EventFragment extends Fragment {
    private RecyclerView mEventRecyclerView;
    private EventAdapter mAdapter;
    private EventViewModel mViewModel;

    FloatingActionButton mFabAddEvent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEventRecyclerView = view.findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new EventAdapter(getContext());
        mEventRecyclerView.setAdapter(mAdapter);

        mFabAddEvent = view.findViewById(R.id.fabAdd);

        mFabAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEventActivity.class);
                startActivity(intent);

            }
        });

        intiViewModel();
        mViewModel.getEventList();

    }

    public void intiViewModel(){
        mViewModel = new ViewModelProvider(getActivity()).get(EventViewModel.class);

        final Observer<List<Event>> eventListObserver = new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                mAdapter.setEventList(events);
            }
        };

        mViewModel.getEventList().observe(getActivity(), eventListObserver);
    }

    //holder
    private class EventHolder extends RecyclerView.ViewHolder{
        private TextView mEventTitle;
        private TextView mStartTime;
        private TextView mEndTime;
        private Event mEvent;

        public EventHolder(View itemView){
            super(itemView);

            mEventTitle = itemView.findViewById(R.id.tv_title);
            mStartTime = itemView.findViewById(R.id.tv_start_time);
            mEndTime = itemView.findViewById(R.id.tv_end_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View v) {
                        Intent intent = ViewEventActivity.newIntent(getActivity(), mEvent);
                        startActivity(intent);
                }
            });
        }

        public void getEvent(Event event){
            mEvent = event;
        }
    }

    //Adapter
    private class EventAdapter extends RecyclerView.Adapter<EventHolder>{
        private Context context;
        private List<Event> eventList;
        private HandlerEventClick clickListener;

        public EventAdapter (Context context){
            this.context = context;
            //.clickListener = clickListener;
        }

        public void setEventList(List<Event> eventList){
            this.eventList = eventList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.event_list, parent, false);
            return new EventHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventHolder holder, int position) {
            holder.getEvent(eventList.get(position));
            holder.mEventTitle.setText(eventList.get(position).getName());
            holder.mStartTime.setText(eventList.get(position).startDateToString());
            holder.mEndTime.setText(eventList.get(position).endDateToString());
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
