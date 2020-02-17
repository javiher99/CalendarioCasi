package com.example.googlecalendar.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.googlecalendar.R;

public class SettingsActivity extends AppCompatActivity {

    EditText etGmail, etUserName, etPassword;
    TextView tvG, tvU, tvP;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();
        cargarPreferencias();
        saveSharedPreferences();
    }

    private void cargarPreferencias() {
        SharedPreferences preferences = getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);

        String user = preferences.getString("user", "No existe informacion");
        String pass = preferences.getString("pass", "No existe la informacion");
        String gmail = preferences.getString("gmail", "No existe la informacion");

        etUserName.setText(user);
        etPassword.setText(pass);
        etGmail.setText(gmail);
    }

    private void saveSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);

        String usuario = etUserName.getText().toString();
        String gmail = etGmail.getText().toString();
        String pass = etPassword.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", usuario);
        editor.putString("gmail", gmail);
        editor.putString("pass", pass);

        etUserName.setText(usuario);
        etGmail.setText(gmail);
        etPassword.setText(pass);

        editor.commit();
    }

    private void init() {
        etGmail = findViewById(R.id.etGamil);
        etUserName = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPass);

        tvG = findViewById(R.id.tvG);
        tvP = findViewById(R.id.tvP);
        tvU = findViewById(R.id.tvU);
    }


}
