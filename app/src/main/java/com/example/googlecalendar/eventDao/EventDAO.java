package com.example.googlecalendar.eventDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.googlecalendar.entity.Event;

import java.util.List;

@Dao
public interface EventDAO  {

    @Delete
    int delete(Event evento);

    @Update
    int edit (Event evento);

    @Insert
    long insert(Event evento);

    @Query("select * from event where id = :id")
    Event get(int id);

    @Query("select * from event order by fecha, hora, nombre, id desc")
    List<Event> getAll();

    @Query("select * from event order by fecha, hora, nombre, id desc")
    LiveData<List<Event>> getAllLive();

    @Query("select * from event order by id asc")
    LiveData<List<Event>> getAllEvents();

    @Query("select * from event where id == :idEvent")
    LiveData<Event> getOneEvent(long idEvent);

}
