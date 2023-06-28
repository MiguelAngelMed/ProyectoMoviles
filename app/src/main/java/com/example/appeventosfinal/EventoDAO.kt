package com.example.accesoadatos

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventoDAO {
    @Insert
    fun create(evento: Evento)
    @Update
    fun update(evento: Evento)
    @Query("DELETE FROM Evento WHERE idEvento = :id")
    fun deleteEventoById(id: Int)
    @Query("select * from Evento")
    fun readAll(): List<Evento>
    @Query("SELECT * FROM Evento WHERE idEvento = :id")
    fun getEventoById(id: Int): Evento
    @Query("SELECT * FROM Evento WHERE nombreEvento = :id")
    fun getEventoByNombre(id: String): Evento


}