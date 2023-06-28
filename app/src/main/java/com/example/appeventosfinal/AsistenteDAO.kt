package com.example.accesoadatos

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AsistenteDAO {
    //Inserta un asistente a la tabla Asistente
    @Insert
    fun create(asistente: Asistente)
    //Actualiza un asistente
    @Update
    fun update(asistente: Asistente)

    //elimina a un asistente pasandole un id
    @Query("DELETE FROM Asistente WHERE idAsistente = :idAsistente")
    fun deleteAsistenteByID(idAsistente: Int)

    //obtiene todos los asistente en la base de datos
    @Query("select * from Asistente")
    fun readAll(): List<Asistente>

    //obtiene un asistente por id
    @Query("select * from Asistente where idAsistente LIKE :idAsistente")
    fun readAsistenteById(idAsistente: Int): Asistente
}