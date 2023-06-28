package com.example.appeventosfinal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.accesoadatos.*

@Database(entities = [Asistente::class,Evento::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventoDao(): EventoDAO
    abstract fun asistenteDao(): AsistenteDAO
    abstract fun eventoAsistenteDao(): EventoAsistenteDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "UserAndroid"
                    ).build()

                }
                INSTANCE = instance
                return instance

            }
        }


    }
}