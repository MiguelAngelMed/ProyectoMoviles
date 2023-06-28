package com.example.appeventosfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.accesoadatos.Asistente
import com.example.accesoadatos.AsistenteDAO
import com.example.accesoadatos.Evento
import com.example.accesoadatos.EventoDAO
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VerAsistentes : AppCompatActivity() {
    private lateinit var asistenteDao: AsistenteDAO
    private lateinit var asistenteL: List<Asistente>
    private lateinit var arrayList: ArrayList<Asistente>
    lateinit var toggle : ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_asistentes)

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


        val btnAddAsistente: Button = findViewById(R.id.btnAddAsistente)
        btnAddAsistente.setOnClickListener(){
            val intentVerAsistentes= Intent(this, AgregarAsistente::class.java)
            startActivity(intentVerAsistentes)
        }

        val appDatabase = AppDatabase.getInstance(applicationContext)
        asistenteDao = appDatabase.asistenteDao()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch(Dispatchers.IO){
            asistenteL = asistenteDao.readAll()
            arrayList = ArrayList(asistenteL)

            withContext(Dispatchers.Main) {
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                val adapter = AsistenteAdapter(arrayList)

                recyclerView.adapter = adapter
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}