package com.example.googlecalendar.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.googlecalendar.Calendar;
import com.example.googlecalendar.database.EventDatabase;
import com.example.googlecalendar.entity.Event;
import com.example.googlecalendar.eventDao.EventDAO;
import com.example.googlecalendar.model.data.EventData;
import com.example.googlecalendar.model.rest.EventClientApi;
import com.example.googlecalendar.view.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private final String GET = "GET";

    private EventClientApi apiClient;

    private final String url = "3.86.238.118";


    private Long id;

    private EventDAO eventDAO;
    private LiveData<List<Event>> eventsLive, eventsLiveId, eventId;

    // Retrofit
    private ArrayList<EventData> listaEventos = new ArrayList<>();

    private MutableLiveData<List<EventData>> mutableEventsList = new MutableLiveData<>();

    public Repository(Context context) {
        EventDatabase db = EventDatabase.getDatabase(context);
        eventDAO = db.getEventDAO();
        eventsLive = eventDAO.getAllLive();
        eventsLiveId = eventDAO.getAllEvents();

        // Retrofit

        recoverApi();

    }

    // Retrofit

    private void recoverApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+url+"/web/calendar/public/api/event/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiClient = retrofit.create(EventClientApi.class);
    }

    public void getAllEventsList() {
        Call<ArrayList<EventData>> callEvents = apiClient.getEventsData();
        callEvents.enqueue(new Callback<ArrayList<EventData>>() {
            @Override
            public void onResponse(Call<ArrayList<EventData>> call, Response<ArrayList<EventData>> response) {
                Log.d(GET, response.body().toString());
                listaEventos = response.body();
                mutableEventsList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<EventData>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    public void añadirEvento(EventData eventData) {
        Call<Long> callAñadirEvento = apiClient.postEvent(eventData);
        callAñadirEvento.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                // Actualiza lista de eventos
                Log.v("xxxyyy", String.valueOf(response.body()));
                //if (response.body() != 0) {
                    getAllEventsList();
                //}
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    public void borrarEvento(long id){
        Call<Integer> callBorrarEvento = apiClient.deleteEvent(id);
        callBorrarEvento.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                getAllEventsList();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    // Room

    private class EditThread extends AsyncTask<Event, Void, Void> {
        @Override
        protected Void doInBackground(Event... events) {
            eventDAO.edit(events[0]);
            Log.v("xyz", events[0].toString());
            return null;
        }
    }

    public class InsertThread extends AsyncTask<Event, Void, Void> {

        @Override
        protected Void doInBackground(Event... events) {
            eventDAO.insert(events[0]);
            Log.v("xyz", events[0].toString());
            return null;
        }
    }

    public class DeleteThread extends AsyncTask<Event, Void, Void> {

        @Override
        protected Void doInBackground(Event... events) {
            eventDAO.delete(events[0]);
            Log.v("xyz", events[0].toString());
            return null;
        }
    }

    public LiveData<List<Event>> getEventsLive() { return eventsLive; }

    public LiveData<List<Event>> getEventsById() { return eventsLiveId; }

    public  void insertEvent(Event event) { new InsertThread().execute(event); }

    public void deleteEvent(Event event) { new DeleteThread().execute(event); }

    public void editEvent(Event event) { new EditThread().execute(event); }

    public LiveData<List<EventData>> getLiveDataEventsList() { return mutableEventsList; }
}
