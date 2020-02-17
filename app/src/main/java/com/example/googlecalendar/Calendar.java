package com.example.googlecalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.TimePickerDialog;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.googlecalendar.entity.Event;
import com.example.googlecalendar.eventDao.EventDAO;
import com.example.googlecalendar.model.Repository;
import com.example.googlecalendar.model.data.EventData;
import com.example.googlecalendar.view.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Calendar extends AppCompatActivity {

    // Room
    private EventDAO eventDAO;
    private LiveData<List<Event>> eventsLive;

    public static String[] evento = new String[3];

    CalendarView eventCalendar;
    FloatingActionButton createEvent;
    TextView showDateEvent, eventHour;
    EditText eventName;

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";

    //Variables para obtener la hora
    final int hour = 0;
    final int minute = 0;

    private String date;

    private Event event;
    private MainViewModel viewModel;

    private EventData eventoAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event);


        event = new Event();
        eventoAux = new EventData();
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        eventCalendar = findViewById(R.id.eventCalendar);
        createEvent = findViewById(R.id.floatingEvent);
        showDateEvent = findViewById(R.id.showDateEvent);
        eventHour = findViewById(R.id.eventHour);
        eventName = findViewById(R.id.eventName);

        eventCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + (month=month+1) + "/" + year;
                showDateEvent.setText(date);
            }
        });

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent();
            }
        });

        eventHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTimePicker();
            }

        });


    }

    private void saveEvent() {
        String nombreEvento = String.valueOf(eventName.getText());
        String fecha = String.valueOf(showDateEvent.getText());
        String hora = String.valueOf(eventHour.getText());

        if (fecha.equalsIgnoreCase("") || hora.equalsIgnoreCase("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Debes porner al menos Fecha y Hora de evento", Toast. LENGTH_SHORT); toast. show();
        } else if (nombreEvento.equalsIgnoreCase("")) {
            event.setNombre("(Sin titulo)");
            event.setFecha(fecha);
            event.setHora(hora);
            eventoAux.setNombre("(Sin titulo)");
            eventoAux.setFecha(fecha);
            eventoAux.setHora(hora);
            viewModel.insert(event);
            viewModel.añadirEventoData(eventoAux);
            Intent i = new Intent(this, MainActivity.class);
              startActivity(i);
        } else {
            event.setNombre(nombreEvento);
            event.setFecha(fecha);
            event.setHora(hora);
            eventoAux.setNombre(nombreEvento);
            eventoAux.setFecha(fecha);
            eventoAux.setHora(hora);
            viewModel.insert(event);
            viewModel.añadirEventoData(eventoAux);
            Intent i = new Intent(this, MainActivity.class);
             startActivity(i);
        }

    }

    private void sendGoogleCalendarEvent() {

    }

    private void callTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if (hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseada
                eventHour.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    // Room




}


