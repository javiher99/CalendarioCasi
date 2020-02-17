package com.example.googlecalendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.googlecalendar.entity.Event;
import com.example.googlecalendar.model.Repository;
import com.example.googlecalendar.model.data.EventData;
import com.example.googlecalendar.model.rest.EventClientApi;
import com.example.googlecalendar.settings.SettingsActivity;
import com.example.googlecalendar.view.EventAdapter;
import com.example.googlecalendar.view.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button btCalendar;

    private final String GET = "GET";

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RecyclerView eventList = findViewById(R.id.eventList);
        eventList.setLayoutManager(new LinearLayoutManager(this));
        final EventAdapter adapter = new EventAdapter(this);
        eventList.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //viewModel = ViewModelProviders.of(this).get(MainViewModel.class); Deprecated

        viewModel.getEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable final List<Event> events) {
                adapter.setEvents(events);
            }
        });

        btCalendar = findViewById(R.id.btCalendar);
        btCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irCalendario();
            }
        });

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

    }

    // Recoge los eventos de la BD
    private void getAllEventData() {
        viewModel.getLiveDataEventsList().observe(this, new Observer<List<EventData>>() {
            @Override
            public void onChanged(List<EventData> eventData) {
                ArrayList<EventData> eventDataAux = new ArrayList<>();
                for (int i = 0; i < eventData.size(); i++) {
                    eventDataAux.add(eventData.get(i));
                }
            }

        });
    }

    private void irEdit() {
        Intent intent = new Intent(this, EditEvent.class);
        startActivity(intent);
    }

    private void irCalendario() {
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item1) {
            Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}
