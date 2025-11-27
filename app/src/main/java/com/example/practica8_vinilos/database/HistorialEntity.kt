package com.example.practica8_vinilos.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial")
data class HistorialEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fecha: String,
    val total: Double,
    val resumenCompra: String
)