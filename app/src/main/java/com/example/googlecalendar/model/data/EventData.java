package com.example.googlecalendar.model.data;

import com.google.gson.annotations.SerializedName;

public class EventData {

    private long id;

    private String nombre;

    private String hora;

    @SerializedName("body")
    private String fecha;

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHora() {
        return hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "EventData{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", hora='" + hora + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
