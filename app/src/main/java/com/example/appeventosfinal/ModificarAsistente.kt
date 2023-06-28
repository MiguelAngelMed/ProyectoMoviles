package com.example.appeventosfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.accesoadatos.Asistente
import com.example.accesoadatos.AsistenteDAO
import com.example.accesoadatos.Evento
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModificarAsistente : AppCompatActivity() {

    private lateinit var asistenteDao: AsistenteDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_asistente)


        val appDatabase = AppDatabase.getInstance(applicationContext)
        asistenteDao = appDatabase.asistenteDao()

        val asistenteJson = intent.getStringExtra("asistente")
        val gson = Gson()
        val asistente = gson.fromJson<Asistente>(asistenteJson, Asistente::class.java)

        val inputNombre = findViewById<EditText>(R.id.inputNombre)
        inputNombre.setText(asistente.nombreAsistente)

        val inputPaterno = findViewById<EditText>(R.id.inputPaterno)
        inputPaterno.setText(asistente.paternoAsistente)

        val inputMaterno = findViewById<EditText>(R.id.inputMaterno)
        inputMaterno.setText(asistente.maternoAsistente)

        val inputId = findViewById<EditText>(R.id.inputIdEvento)
        inputId.setText(asistente.idEvento.toString())

        val btnGuardar = findViewById<Button>(R.id.btnGuardarA)

        btnGuardar.setOnClickListener(){
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val asistenteActualizar = Asistente(asistente.idAsistente,inputNombre.text.toString(),inputPaterno.text.toString(),inputMaterno.text.toString(),inputId.text.toString().toInt())
                    asistenteDao.update(asistenteActualizar)

                }
            }

            Toast.makeText(this, "Se ha actualizado el asistente", Toast.LENGTH_SHORT).show()

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

    }
}