package com.example.a202sgi_groupassignmentproject;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private EventRepository mRepository;
    private LiveData<List<Event>> mEventList;
    private LiveData<List<Event>> mWeeklyEventList;
    private LiveData<List<Event>> mTodoList;

    public EventViewModel (Application application){
        super(application);
        mRepository = new EventRepository(application);
        mEventList = mRepository.getEventList();
        mWeeklyEventList = mRepository.getWeeklyEventList();
        mTodoList = mRepository.getTodoList();
    }

    LiveData<List<Event>> getEventList(){
        return mEventList;
    }

    LiveData<List<Event>> getWeeklyEventList(){
        return mWeeklyEventList;
    }

    LiveData<List<Event>> getTodoList(){
        return mTodoList;
    }

    public void insertEvent(Event event){
        mRepository.insert(event);
    }

    public void deleteEvent(Event event){
        mRepository.delete(event);
    }

}
