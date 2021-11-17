package com.example.a202sgi_groupassignmentproject;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventRepository {
    private EventDao mEventDao;
    private LiveData<List<Event>> mEventList;
    private LiveData<List<Event>> mWeeklyEventList;
    private LiveData<List<Event>> mTodoList;

    EventRepository(Application application){
        EventRoomDatabase db =EventRoomDatabase.getDBInstance(application);
        mEventDao = db.eventDao();
        mEventList = mEventDao.getAllEvents();
        mWeeklyEventList = mEventDao.getAllWeeklyEvents();
        mTodoList = mEventDao.getAllTodoEvents();
    }

    public LiveData<List<Event>> getEventList(){
        return mEventList;
    }

    public LiveData<List<Event>> getWeeklyEventList(){
        return mWeeklyEventList;
    }

    public LiveData<List<Event>> getTodoList(){
        return mTodoList;
    }

    public void insert(Event event){
        new insertAsyncTask(mEventDao).execute(event);
    }

    public void delete(Event event){
        new deleteAsyncTask(mEventDao).execute(event);
    }

    private static class insertAsyncTask extends android.os.AsyncTask<Event, Void, Void>{
        private EventDao mAsyncTaskDao;

        insertAsyncTask(EventDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            mAsyncTaskDao.insert(events[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends android.os.AsyncTask<Event, Void, Void>{
        private EventDao mAsyncTaskDao;

        deleteAsyncTask(EventDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            mAsyncTaskDao.deleteEvent(events[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends android.os.AsyncTask<Event, Void, Void>{
        private EventDao mAsyncTaskDao;

        updateAsyncTask(EventDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            mAsyncTaskDao.updateEvent(events[0]);
            return null;
        }
    }
}
