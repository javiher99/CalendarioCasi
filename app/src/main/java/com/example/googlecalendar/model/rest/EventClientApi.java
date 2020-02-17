package com.example.googlecalendar.model.rest;

import androidx.room.Delete;

import com.example.googlecalendar.model.data.EventData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventClientApi {

    @GET("event")
    Call<ArrayList<EventData>> getEventsData();

    @POST("event")
    Call<Long> postEvent(@Body EventData events);

    @DELETE("event/{id}")
    Call<Integer> deleteEvent(@Path("id") long id);

}
