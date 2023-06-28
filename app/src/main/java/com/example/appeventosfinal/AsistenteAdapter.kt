package com.example.appeventosfinal

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.accesoadatos.Asistente
import com.example.accesoadatos.Evento
import com.google.gson.Gson

class AsistenteAdapter (val listaAsistente: ArrayList<Asistente>): RecyclerView.Adapter<AsistenteAdapter.ManejadorVista>() {
    class ManejadorVista(elementoVista: View) : RecyclerView.ViewHolder(elementoVista){

        var nombreAsistente : TextView
        init {
            nombreAsistente = elementoVista.findViewById(R.id.textoCard)
        }
    }

    /* esta función se utiliza para crear y devolver un nuevo ViewHolder que contiene la vista inflada del diseño de cada elemento en la lista*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManejadorVista {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.card_item_layout,parent, false)
        return ManejadorVista(vista)
    }

    override fun getItemCount(): Int {
        return listaAsistente.size
    }

    override fun onBindViewHolder(holder: ManejadorVista, position: Int) {
        val asistente = listaAsistente[position]
        holder.nombreAsistente.text = asistente.nombreAsistente + " "+asistente.paternoAsistente
        holder.itemView.tag = asistente

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetalleAsistente::class.java)
            val gson = Gson()
            val asistenteJson = gson.toJson(asistente)
            intent.putExtra("asistente",asistenteJson)
            holder.itemView.context.startActivity(intent)
        }
    }
}