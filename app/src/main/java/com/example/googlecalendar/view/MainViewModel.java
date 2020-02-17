package com.example.googlecalendar.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.googlecalendar.entity.Event;
import com.example.googlecalendar.model.Repository;
import com.example.googlecalendar.model.data.EventData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;

    private LiveData<List<Event>> events, eventId;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        events = repository.getEventsById();
    }

    // Retrofit

    public LiveData<List<EventData>> getLiveDataEventsList() {
        return repository.getLiveDataEventsList();
    }

    public void añadirEventoData(EventData eventData) {
        repository.añadirEvento(eventData);
    }

    public void borrarEventoData(long id) {
        repository.borrarEvento(id);
    }
    // Room

    public LiveData<List<Event>> getEvents() { return events; }

    public void insert(Event event) { repository.insertEvent(event);}

    public void delete(Event event) { repository.deleteEvent(event);}

    public void edit(Event event) { repository.editEvent(event); }
}
