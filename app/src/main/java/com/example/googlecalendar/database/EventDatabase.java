package com.example.googlecalendar.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.googlecalendar.entity.Event;
import com.example.googlecalendar.eventDao.EventDAO;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class EventDatabase extends RoomDatabase {

    public abstract EventDAO getEventDAO();

    private static volatile EventDatabase INSTANCIA;

    public static EventDatabase getDatabase(final Context context) {
        if (INSTANCIA == null) {
            synchronized (EventDatabase.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context.getApplicationContext(),
                            EventDatabase.class, "event.sqlite").build();
                }

            }
        }
        return INSTANCIA;
    }
}
