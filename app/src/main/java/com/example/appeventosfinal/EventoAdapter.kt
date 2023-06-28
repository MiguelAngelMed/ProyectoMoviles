package com.example.appeventosfinal

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.accesoadatos.Evento
import com.google.gson.Gson

class EventoAdapter(val listaEventos: ArrayList<Evento>): RecyclerView.Adapter<EventoAdapter.ManejadorVista>() {
    class ManejadorVista(elementoVista: View) : RecyclerView.ViewHolder(elementoVista){

        var nombreEvento : TextView
        init {
            nombreEvento = elementoVista.findViewById(R.id.textoCard)
        }
    }

    /* esta función se utiliza para crear y devolver un nuevo ViewHolder que contiene la vista inflada del diseño de cada elemento en la lista*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManejadorVista {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.card_item_layout,parent, false)
        return ManejadorVista(vista)
    }

    override fun getItemCount(): Int {
        return listaEventos.size
    }

    override fun onBindViewHolder(holder: ManejadorVista, position: Int) {
        val evento = listaEventos[position]
        holder.nombreEvento.text = evento.nombreEvento
        holder.itemView.tag = evento

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetalleEvento::class.java)
            val gson = Gson()
            val eventoJson = gson.toJson(evento)
            intent.putExtra("evento",eventoJson)
            holder.itemView.context.startActivity(intent)
        }
    }
}