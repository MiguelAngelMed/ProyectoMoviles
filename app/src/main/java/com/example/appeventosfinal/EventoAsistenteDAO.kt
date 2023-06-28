package com.example.accesoadatos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface EventoAsistenteDAO {
    @Transaction
    @Query("SELECT * FROM Evento WHERE idEvento = :idEvento")
    fun getEventoAsistenteById(idEvento: Int): EventoAsistente
}