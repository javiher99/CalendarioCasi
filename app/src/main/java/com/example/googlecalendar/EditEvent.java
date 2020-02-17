package com.example.googlecalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.googlecalendar.entity.Event;
import com.example.googlecalendar.eventDao.EventDAO;
import com.example.googlecalendar.view.MainViewModel;

public class EditEvent extends AppCompatActivity {

    Button btSave, btDelete;
    EditText editNombre;
    TextView editHora;

    Event event, eventAux;

    Intent e;

    private final String NOMBRE_EVENTO = "NOMBRE_EVENTO";
    private final String ID_EVENTO = "ID_EVENTO";
    private final String HORA_EVENTO = "HORA_EVENTO";

    private MainViewModel viewModel;

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";

    private static final String TAG = "EditEventActivity";

    private String nombreEvento;
    private String horaEvento;
    private Long idEvento;
    private String idAux;

    final int hour = 0;
    final int minute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        init();
        getCommingIntent();



        editHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callTimePicker();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarEvento();
            }
        });

    }

    private void getCommingIntent() {
        Log.v(TAG, "get intent");
        if (getIntent().hasExtra(NOMBRE_EVENTO) && getIntent().hasExtra(HORA_EVENTO) && getIntent().hasExtra(ID_EVENTO)) {
            Log.v(TAG, "Hay intent");

            nombreEvento = getIntent().getStringExtra(NOMBRE_EVENTO);
            horaEvento = getIntent().getStringExtra(HORA_EVENTO);
            idAux = getIntent().getStringExtra(ID_EVENTO);

            idEvento = Long.valueOf(idAux);
            editNombre.setText(nombreEvento);
            editHora.setText(horaEvento);

        }
    }

    private void guardarCambios() {
        e = getIntent();
        event = e.getParcelableExtra("EVENTO");
        eventAux = event;
        String nombre = String.valueOf(editNombre.getText());
        String hora = String.valueOf(editHora.getText());
        Log.v(TAG, "nombre: " + nombre + " hora: " + hora);
        eventAux.setNombre(nombre);
        eventAux.setHora(hora);
        viewModel.edit(eventAux);
        Toast.makeText(this, "Cambios guardados con exito", Toast.LENGTH_SHORT).show();
        Intent f = new Intent(this, MainActivity.class);
        startActivity(f);
    }

    private void borrarEvento() {
        Log.v(TAG, String.valueOf(idEvento));
        e = getIntent();
        event = e.getParcelableExtra("EVENTO");
        viewModel.delete(event);
        //Retrofit
        viewModel.borrarEventoData(idEvento);
        Toast.makeText(this, "Borrado con exito", Toast.LENGTH_SHORT).show();
        Intent f = new Intent(this, MainActivity.class);
        startActivity(f);
        finish();
    }

    private void init() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        editNombre = findViewById(R.id.editNombre);
        editHora = findViewById(R.id.editHora);
        btDelete = findViewById(R.id.btDeleteEvent);
        btSave = findViewById(R.id.btGuardarEdit);
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
                editHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

}
