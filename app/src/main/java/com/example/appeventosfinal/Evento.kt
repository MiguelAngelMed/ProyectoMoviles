package com.example.accesoadatos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Evento")
data class Evento (
    @PrimaryKey(autoGenerate = true)
    var idEvento: Int,

    @ColumnInfo(name = "nombreEvento")
    var nombreEvento: String,

    @ColumnInfo(name =  "descripcionEvento")
    var descripcionEvento: String,

    @ColumnInfo(name = "fechaEvento")
    var fechaEvento: String

)