package com.example.practica8_vinilos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica8_vinilos.database.HistorialEntity // <--- IMPORTANTE
import com.example.practica8_vinilos.database.PedidoDao
import com.example.practica8_vinilos.database.PedidoEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat // <--- Para la fecha
import java.util.Date
import java.util.Locale

class VinilosViewModel(private val dao: PedidoDao) : ViewModel() {

    // ESTADO 1: Lista de pedidos del carrito (Tu código original)
    val pedidos: StateFlow<List<PedidoEntity>> = dao.obtenerPedidos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // ESTADO 2: Lista del Historial de compras (NUEVO)
    // Esto conecta con la nueva consulta que agregamos al DAO
    val historial: StateFlow<List<HistorialEntity>> = dao.obtenerHistorial()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // FUNCIÓN 1: Agregar al carrito
    fun agregarAlCarrito(album: String, artista: String, precio: Double) {
        viewModelScope.launch {
            val nuevoPedido = PedidoEntity(
                album = album,
                artista = artista,
                precio = precio,
                cantidad = 1
            )
            dao.insertarPedido(nuevoPedido)
        }
    }

    // FUNCIÓN 2: Aumentar cantidad
    fun incrementarCantidad(pedido: PedidoEntity) {
        viewModelScope.launch {
            val pedidoActualizado = pedido.copy(cantidad = pedido.cantidad + 1)
            dao.actualizarPedido(pedidoActualizado)
        }
    }

    // FUNCIÓN 3: Disminuir cantidad
    fun decrementarCantidad(pedido: PedidoEntity) {
        viewModelScope.launch {
            if (pedido.cantidad > 1) {
                val pedidoActualizado = pedido.copy(cantidad = pedido.cantidad - 1)
                dao.actualizarPedido(pedidoActualizado)
            } else {
                dao.eliminarPedido(pedido)
            }
        }
    }

    // FUNCIÓN 4: Eliminar directo
    fun eliminarPedido(pedido: PedidoEntity) {
        viewModelScope.launch {
            dao.eliminarPedido(pedido)
        }
    }

    // --- FUNCIÓN 5 (NUEVA): FINALIZAR COMPRA ---
    // Esta función hace la magia de mover todo al historial
    fun finalizarCompra() {
        viewModelScope.launch {
            val listaActual = pedidos.value

            // Solo procedemos si el carrito NO está vacío
            if (listaActual.isNotEmpty()) {

                // 1. Calcular cuánto pagó en total
                val total = listaActual.sumOf { it.precio * it.cantidad }

                // 2. Crear un texto resumen (Ej: "Album1 (x2), Album2 (x1)")
                val resumen = listaActual.joinToString(", ") { "${it.album} (x${it.cantidad})" }

                // 3. Obtener la fecha y hora actual
                val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

                // 4. Crear el objeto para guardar en la tabla Historial
                val compra = HistorialEntity(
                    fecha = fecha,
                    total = total,
                    resumenCompra = resumen
                )

                // 5. Guardar en la BD
                dao.guardarCompra(compra)

                // 6. Vaciar el carrito (Borrar tabla pedidos)
                dao.vaciarCarrito()
            }
        }
    }
}