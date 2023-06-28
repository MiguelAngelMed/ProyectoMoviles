package com.example.appeventosfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.accesoadatos.Evento
import com.example.accesoadatos.EventoDAO
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoficarEvento : AppCompatActivity() {

    private lateinit var eventoDao: EventoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moficar_evento)

        val appDatabase = AppDatabase.getInstance(applicationContext)
        eventoDao = appDatabase.eventoDao()

        val eventoJson = intent.getStringExtra("evento")
        val gson = Gson()
        val evento = gson.fromJson<Evento>(eventoJson, Evento::class.java)

        val inputNombre = findViewById<EditText>(R.id.inputNombre)
        inputNombre.setText(evento.nombreEvento)

        val inputDescripcion = findViewById<EditText>(R.id.inputDescripcion)
        inputDescripcion.setText(evento.descripcionEvento)

        val inputFecha = findViewById<EditText>(R.id.inputFecha)
        inputFecha.setText(evento.fechaEvento)


        val btnGuardar = findViewById<Button>(R.id.btnActEv)

        btnGuardar.setOnClickListener(){
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val eventActualizar = Evento(evento.idEvento,inputNombre.text.toString(),inputDescripcion.text.toString(),inputFecha.text.toString())
                    eventoDao.update(eventActualizar)

                }
            }

            Toast.makeText(this, "Se ha actualizado el evento", Toast.LENGTH_SHORT).show()

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


    }
}