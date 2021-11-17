package com.example.a202sgi_groupassignmentproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {

    @Insert
    void insert(Event... event);

    @Query("Select * from event_table Where weekly = 0 AND todo = 0 Order by `Start Date` ASC")
    LiveData<List<Event>> getAllEvents();

    @Query("Select * from event_table Where weekly = 1 AND todo = 0 Order by `Day of Week` ASC ")
    LiveData<List<Event>> getAllWeeklyEvents();

    @Query("Select * from event_table Where todo = 1 AND weekly = 0 Order by `Day of Week` ASC ")
    LiveData<List<Event>> getAllTodoEvents();

    @Update
    void updateEvent(Event event);

    @Delete
    void deleteEvent(Event event);

    @Query("Delete from event_table")
    void deleteAll();
}
