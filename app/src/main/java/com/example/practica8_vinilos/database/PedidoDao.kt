package com.example.practica8_vinilos.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {
    @Query("SELECT * FROM pedidos")
    fun obtenerPedidos(): Flow<List<PedidoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPedido(pedido: PedidoEntity)

    @Update
    suspend fun actualizarPedido(pedido: PedidoEntity)

    @Delete
    suspend fun eliminarPedido(pedido: PedidoEntity)

    @Query("DELETE FROM pedidos")
    suspend fun vaciarCarrito()


    @Query("SELECT * FROM historial ORDER BY id DESC")
    fun obtenerHistorial(): Flow<List<HistorialEntity>>

    @Insert
    suspend fun guardarCompra(compra: HistorialEntity)
}