package com.example.googlecalendar.view;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.googlecalendar.EditEvent;
import com.example.googlecalendar.MainActivity;
import com.example.googlecalendar.R;
import com.example.googlecalendar.entity.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private LayoutInflater inflater;
    private List<Event> eventList;
    private int contador = 0;

    private final String NOMBRE_EVENTO = "NOMBRE_EVENTO";
    private final String ID_EVENTO = "ID_EVENTO";
    private final String HORA_EVENTO = "HORA_EVENTO";
    private final String EVENTO = "EVENTO";

    public EventAdapter(Context context) { inflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        contador++;
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        if (eventList != null) {
            Event current = eventList.get(position);
            holder.tvItemNombre.setText(current.getNombre());
            holder.tvItemFecha.setText(current.getFecha());
            holder.tvItemHora.setText(current.getHora());
            holder.cl.setOnClickListener(v -> {
                //Toast.makeText(v.getContext(), "has pulsado" + eventList.get(position), Toast.LENGTH_SHORT).show();
                String nombre = current.getNombre();
                String id = String.valueOf(current.getId());
                Log.w("id", id);
                Toast.makeText(v.getContext(), "has pulsado" + id, Toast.LENGTH_SHORT).show();
                String hora = current.getHora();

                Intent intent = new Intent(v.getContext(), EditEvent.class);
                intent.putExtra(EVENTO, current);
                intent.putExtra(NOMBRE_EVENTO, nombre);
                intent.putExtra(HORA_EVENTO, hora);
                intent.putExtra(ID_EVENTO, id);
                v.getContext().startActivity(intent);

            });
        }else {
            holder.tvItemNombre.setText("No event available");
        }
    }

    @Override
    public int getItemCount() {
        int elementos = 0;
        if (eventList != null) {
            elementos = eventList.size();
        }
        return elementos;
    }

    public void setEvents(List<Event> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }


    public class EventViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvItemNombre, tvItemFecha, tvItemHora;
        private final ConstraintLayout cl;

        public EventViewHolder(@NonNull View itemView) {
            super (itemView);
            tvItemNombre = itemView.findViewById(R.id.tvItemNombre);
            tvItemFecha = itemView.findViewById(R.id.tvItemFecha);
            tvItemHora = itemView.findViewById(R.id.tvItemHora);
            cl = itemView.findViewById(R.id.cl);
        }
    }

}
