package com.example.practica8_vinilos.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pedidos")
data class PedidoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val album: String,
    val artista: String,
    val precio: Double,
    val cantidad: Int
)