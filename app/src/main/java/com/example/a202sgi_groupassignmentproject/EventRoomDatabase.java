package com.example.a202sgi_groupassignmentproject;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class EventRoomDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
    public static EventRoomDatabase INSTANCE;

    public static EventRoomDatabase getDBInstance(Context context){
        if(INSTANCE == null){
            synchronized (EventRoomDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), EventRoomDatabase.class, "event_database").build();
            }
        }
        return INSTANCE;
    }
}
