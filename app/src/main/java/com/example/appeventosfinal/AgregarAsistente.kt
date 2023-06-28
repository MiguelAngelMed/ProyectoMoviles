package com.example.appeventosfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.example.accesoadatos.Asistente
import com.example.accesoadatos.AsistenteDAO
import com.example.accesoadatos.Evento
import com.example.accesoadatos.EventoDAO
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgregarAsistente : AppCompatActivity() {

    private lateinit var asistenteDao: AsistenteDAO
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var eventosL: List<Evento>
    private lateinit var eventoDao: EventoDAO
    private lateinit var arrayList: ArrayList<Evento>
    private lateinit var spinnerEvento: Spinner
    lateinit var texto1: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_asistente)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intentEventosM= Intent(this, VerEventos::class.java)
        val intentHomeM= Intent(this, MainActivity::class.java)
        val intentAgregarEventoM= Intent(this, AgregarEvento::class.java)
        val intentAgregarAsistenteM= Intent(this, AgregarAsistente::class.java)
        val intentAsistentesM= Intent(this, VerAsistentes::class.java)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> startActivity(intentHomeM)
                R.id.nav_eventos -> startActivity(intentEventosM)
                R.id.nav_addEvento -> startActivity(intentAgregarEventoM)
                R.id.nav_asistente -> startActivity(intentAsistentesM)
                R.id.nav_addAsistente -> startActivity(intentAgregarAsistenteM)
            }
            true
        }

        val appDatabase = AppDatabase.getInstance(applicationContext)
        asistenteDao = appDatabase.asistenteDao()
        val btnAgregarAsistente = findViewById<Button>(R.id.btnAgregarUnAsistente)

        spinnerEvento = findViewById<Spinner>(R.id.inputIdEvento)
        eventoDao = appDatabase.eventoDao()
        var nombreEventos: MutableList<String> = mutableListOf<String>()
        lifecycleScope.launch(Dispatchers.IO){
            eventosL = eventoDao.readAll()
            arrayList = ArrayList(eventosL)


            for (i in arrayList){
                nombreEventos.add(i.nombreEvento)
            }
        }
        Thread.sleep(1000)
        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, nombreEventos)
        spinnerEvento.adapter=arrayAdapter

        spinnerEvento.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                texto1=nombreEventos[position]

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        btnAgregarAsistente.setOnClickListener(){
            val txtNombre = findViewById<EditText>(R.id.inputNombre)
            val txtApaterno = findViewById<EditText>(R.id.inputPaterno)
            val txtAmaterno = findViewById<EditText>(R.id.inputMaterno)


            lifecycleScope.launch(Dispatchers.IO) {
                var evento1 = eventoDao.getEventoByNombre(texto1)
                val asistenteAgregar = Asistente(0,txtNombre.text.toString(),txtApaterno.text.toString(),txtAmaterno.text.toString(),evento1.idEvento)

                withContext(Dispatchers.IO) {
                    asistenteDao.create(asistenteAgregar)

                }

            }

            Toast.makeText(this, "Se ha agregado el asistente", Toast.LENGTH_SHORT).show()
        }


        val btnRegresar: Button = findViewById(R.id.btnRegresarA)
        btnRegresar.setOnClickListener(){
            val intentEventos= Intent(this, VerAsistentes::class.java)
            startActivity(intentEventos)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}