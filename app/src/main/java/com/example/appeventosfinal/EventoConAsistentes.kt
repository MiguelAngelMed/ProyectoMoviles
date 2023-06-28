package com.example.appeventosfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.accesoadatos.Asistente
import com.example.accesoadatos.EventoAsistente
import com.example.accesoadatos.EventoAsistenteDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventoConAsistentes : AppCompatActivity() {
    private lateinit var eventoConAsistentes: EventoAsistenteDAO
    private lateinit var asistenteL: List<Asistente>
    private lateinit var arrayList: ArrayList<Asistente>
    private lateinit var eventoAsistente: EventoAsistente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evento_con_asistentes)

        val appDatabase = AppDatabase.getInstance(applicationContext)
        eventoConAsistentes = appDatabase.eventoAsistenteDao()

        var idEvento =0
        if (intent.extras != null) {
            val extras = intent.extras
            idEvento = extras?.getInt("id") ?: 0
            val nomEvento = extras?.getString("nombre") ?: ""
            val txtNom = findViewById<TextView>(R.id.eventotal)
            txtNom.setText(nomEvento)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch(Dispatchers.IO){
            eventoAsistente = eventoConAsistentes.getEventoAsistenteById(idEvento)
            asistenteL = eventoAsistente.listaDeAsistentes
            arrayList = ArrayList(asistenteL)

            withContext(Dispatchers.Main) {
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                val adapter = AsistenteAdapter(arrayList)

                recyclerView.adapter = adapter
            }
        }
    }
}