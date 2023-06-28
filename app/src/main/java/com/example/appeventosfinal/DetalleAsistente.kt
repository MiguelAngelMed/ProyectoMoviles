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
import androidx.recyclerview.widget.RecyclerView
import com.example.accesoadatos.Asistente
import com.example.accesoadatos.AsistenteDAO
import com.example.accesoadatos.Evento
import com.example.accesoadatos.EventoDAO
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalleAsistente : AppCompatActivity() {

    private lateinit var asistenteDao: AsistenteDAO
    private lateinit var eventoDao: EventoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_asistente)

        val appDatabase = AppDatabase.getInstance(applicationContext)
        asistenteDao = appDatabase.asistenteDao()
        eventoDao = appDatabase.eventoDao()

        val asistenteJson = intent.getStringExtra("asistente")
        val gson = Gson()
        val asistente = gson.fromJson<Asistente>(asistenteJson, Asistente::class.java)

        val txtId = findViewById<TextView>(R.id.txtId)
        txtId.setText(asistente.idAsistente.toString())

        val txtNombre = findViewById<TextView>(R.id.txtNombreA)
        txtNombre.setText(asistente.nombreAsistente)

        val txtPaterno = findViewById<TextView>(R.id.txtAPaterno)
        txtPaterno.setText(asistente.paternoAsistente)

        val txtMaterno = findViewById<TextView>(R.id.txtAMaterno)
        txtMaterno.setText(asistente.maternoAsistente)

        val txtEvento = findViewById<TextView>(R.id.txtNomEvento)

        lifecycleScope.launch(Dispatchers.IO){

            val obtenerEvento:Evento = eventoDao.getEventoById(asistente.idEvento)
            withContext(Dispatchers.Main) {
               txtEvento.setText(obtenerEvento.nombreEvento)
            }
        }

        val btnEliminar = findViewById<Button>(R.id.btnEliminarAsistente)
        btnEliminar.setOnClickListener(){
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                   asistenteDao.deleteAsistenteByID(asistente.idAsistente)

                }
            }

            Toast.makeText(this, "Se ha eliminado el asistente", Toast.LENGTH_SHORT).show()

            // Crear un Handler asociado al Looper principal
            val handler = Handler(Looper.getMainLooper())

            // Definir la acción a realizar después de 2 segundos
            val delayMillis: Long = 2000 // 2 segundos

            handler.postDelayed({
                // Crear y lanzar la Intent aquí
                val intent = Intent(this, VerAsistentes::class.java)
                startActivity(intent)
            }, delayMillis)
        }


        val btnActualizar = findViewById<Button>(R.id.btnActualizarAsistente)
        btnActualizar.setOnClickListener(){
            val intentAcutalizarEvento= Intent(this, ModificarAsistente::class.java)
            val gson1 = Gson()
            val asistenteJson1 = gson1.toJson(asistente)
            intentAcutalizarEvento.putExtra("asistente",asistenteJson1)
            startActivity(intentAcutalizarEvento)
        }


    }
}