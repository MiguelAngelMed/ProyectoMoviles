package com.example.appeventosfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.accesoadatos.Evento
import com.example.accesoadatos.EventoDAO
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalleEvento : AppCompatActivity() {
    private lateinit var eventoDao: EventoDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_evento)

        val appDatabase = AppDatabase.getInstance(applicationContext)
        eventoDao = appDatabase.eventoDao()

        val eventoJson = intent.getStringExtra("evento")
        val gson = Gson()
        val evento = gson.fromJson<Evento>(eventoJson, Evento::class.java)

        val txtId = findViewById<TextView>(R.id.txtId)
        txtId.text = evento.idEvento.toString()

        val txtNombre = findViewById<TextView>(R.id.txtNombre)
        txtNombre.text = evento.nombreEvento

        val txtDes = findViewById<TextView>(R.id.txtDescripcion)
        txtDes.text = evento.descripcionEvento

        val txtFecha= findViewById<TextView>(R.id.txtFecha)
        txtFecha.text = evento.fechaEvento

        val btnEliminar = findViewById<Button>(R.id.btnEliminarEvento)
        btnEliminar.setOnClickListener(){
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    eventoDao.deleteEventoById(evento.idEvento)

                }
            }

            Toast.makeText(this, "Se ha eliminado el evento", Toast.LENGTH_SHORT).show()

            // Crear un Handler asociado al Looper principal
            val handler = Handler(Looper.getMainLooper())

            // Definir la acción a realizar después de 2 segundos
            val delayMillis: Long = 2000 // 2 segundos

            handler.postDelayed({
                // Crear y lanzar la Intent aquí
                val intent = Intent(this, VerEventos::class.java)
                startActivity(intent)
            }, delayMillis)
        }

        val btnActualizar = findViewById<Button>(R.id.btnActualizar)
        btnActualizar.setOnClickListener(){
            val intentAcutalizarEvento= Intent(this, MoficarEvento::class.java)
            val gson1 = Gson()
            val eventoJson1 = gson1.toJson(evento)
            intentAcutalizarEvento.putExtra("evento",eventoJson1)
            startActivity(intentAcutalizarEvento)
        }

        val btnConAsistente = findViewById<Button>(R.id.btnVerAsistente)

        btnConAsistente.setOnClickListener(){
            val intentListaAsistentes= Intent(this, EventoConAsistentes::class.java)
            intentListaAsistentes.putExtra("id",evento.idEvento)
            intentListaAsistentes.putExtra("nombre",evento.nombreEvento)

            startActivity(intentListaAsistentes)
        }






    }
}