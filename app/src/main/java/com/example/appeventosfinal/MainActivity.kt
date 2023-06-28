package com.example.appeventosfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.accesoadatos.*
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var asistenteDao: AsistenteDAO
    private lateinit var eventoDao: EventoDAO
    private lateinit var eventoAsistenteDAO: EventoAsistenteDAO

    lateinit var toggle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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


        val btnEventos: Button = findViewById(R.id.btnEventos)
        btnEventos.setOnClickListener(){
            val intentEventos= Intent(this, VerEventos::class.java)
            startActivity(intentEventos)
        }

        val btnAsistente: Button = findViewById(R.id.btnAsistentes)
        btnAsistente.setOnClickListener(){
            val intentAsistente= Intent(this, VerAsistentes::class.java)
            startActivity(intentAsistente)
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}