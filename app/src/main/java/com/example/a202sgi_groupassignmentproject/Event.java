package com.example.a202sgi_groupassignmentproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "event_table")
public class Event implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int uid;

    @NonNull
    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Start Date")
    @TypeConverters({DateConverter.class})
    private Date startDate;

    @ColumnInfo(name = "End Date")
    @TypeConverters({DateConverter.class})
    private Date endDate;

    @ColumnInfo(name = "Description")
    private String description;

    private boolean weekly;

    private boolean todo;

    @ColumnInfo(name = "Day of Week")
    private int dayOfWeek;

    public Event(){
        startDate = new Date();
        endDate = new Date();
        dayOfWeek = 0;
    }

    public void setUid(int uid){
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isWeekly() {
        return weekly;
    }

    public void setWeekly(boolean weekly) {
        this.weekly = weekly;
    }

    public boolean isTodo() {
        return todo;
    }

    public void setTodo(boolean todo) {
        this.todo = todo;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int weekOfDay) {
        this.dayOfWeek = weekOfDay;
    }

    public String startDateToString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm");

        return sdf.format(startDate);
    }

    public String endDateToString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm ");

        return sdf.format(endDate);
    }

    public String startToEndToString(){
        SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm ");

        return sdf.format(startDate) + "-" + sdf.format(endDate);
    }

    public boolean compareDate(){
        int i = startDate.compareTo(endDate);

        if(i >= 0){
            return false;
        }else if(i < 0){
            return true;
        }
        else{
            return false;
        }
    }

    public String getStringDayOfWeek(){
        switch(dayOfWeek){
            case 1:
                return "Monday";

            case 2:
                return "Tuesday";

            case 3:
                return "Wednesday";

            case 4:
                return "Thursday";

            case 5:
                return "Friday";

            case 6:
                return "Saturday";

            case 7:
                return "Sunday";

            default:
                return "";
        }
    }

    public static class DateConverter{
        @TypeConverter
        public static Long dateToLong(Date date){
            return date == null ? null : date.getTime();
        }

        @TypeConverter
        public static Date longToDate(Long value) {
            return value == null ? null : new Date(value);
        }
    }
}
