package com.example.accesoadatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Asistente")
data class Asistente (
    @PrimaryKey(autoGenerate = true)
    val idAsistente: Int,

    @ColumnInfo(name = "nombreAsistente")
    val nombreAsistente: String,

    @ColumnInfo(name = "paternoAsistente")
    val paternoAsistente: String,

    @ColumnInfo(name="maternoAsistente")
    val maternoAsistente: String,

    @ColumnInfo(name="idEvento")
    val idEvento: Int
)