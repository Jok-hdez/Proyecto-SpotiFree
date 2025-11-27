package com.example.practica8_vinilos.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [PedidoEntity::class, HistorialEntity::class], version = 2)
abstract class VinilosDatabase : RoomDatabase() {
    abstract fun pedidoDao(): PedidoDao
}