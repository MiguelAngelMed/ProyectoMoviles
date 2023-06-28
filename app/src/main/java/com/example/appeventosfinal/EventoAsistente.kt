package com.example.accesoadatos

import androidx.room.Embedded
import androidx.room.Relation


data class EventoAsistente (
    @Embedded
    val evento: Evento,
    @Relation(
        parentColumn = "idEvento",
        entityColumn = "idEvento"
    )
    val listaDeAsistentes: List<Asistente>
)