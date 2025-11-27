package com.example.practica8_vinilos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practica8_vinilos.R
import com.example.practica8_vinilos.VinilosViewModel
import com.example.practica8_vinilos.database.PedidoEntity
import com.example.practica8_vinilos.ui.theme.PrimaryGreen
import com.example.practica8_vinilos.ui.theme.SurfaceDark
import com.example.practica8_vinilos.ui.theme.TextGray

@Composable
fun CarritoScreen(viewModel: VinilosViewModel, onVerHistorialClick: () -> Unit) {
    val pedidos by viewModel.pedidos.collectAsState()
    val total = pedidos.sumOf { it.precio * it.cantidad }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Productos en tu carrito (${pedidos.sumOf { it.cantidad }})",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(pedidos) { pedido ->
                CartItemCard(
                    pedido = pedido,
                    onIncrementar = { viewModel.incrementarCantidad(pedido) },
                    onDecrementar = { viewModel.decrementarCantidad(pedido) },
                    onEliminar = { viewModel.eliminarPedido(pedido) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(180.dp))
            }
        }


        SummarySection(
            total = total,
            modifier = Modifier.align(Alignment.BottomCenter),
            onPagarClick = { viewModel.finalizarCompra() },
            onHistorialClick = onVerHistorialClick
        )
    }
}

@Composable
fun CartItemCard(
    pedido: PedidoEntity,
    onIncrementar: () -> Unit,
    onDecrementar: () -> Unit,
    onEliminar: () -> Unit
) {
    val imagenID = obtenerImagenPorNombre(pedido.album)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imagenID),
                contentDescription = pedido.album,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = pedido.album,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1
                    )
                    Text(
                        text = pedido.artista,
                        fontSize = 14.sp,
                        color = TextGray,
                        maxLines = 1
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${pedido.precio}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    ) {
                        IconButton(onClick = onDecrementar, modifier = Modifier.size(30.dp)) {
                            Text("-", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = "${pedido.cantidad}",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        IconButton(onClick = onIncrementar, modifier = Modifier.size(30.dp)) {
                            Icon(Icons.Default.Add, contentDescription = "+", tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            TextButton(
                onClick = onEliminar,
                modifier = Modifier.align(Alignment.Top)
            ) {
                Text("X", color = Color.Red, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SummarySection(
    total: Double,
    modifier: Modifier = Modifier,
    onPagarClick: () -> Unit,
    onHistorialClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "$${"%.2f".format(total)}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }


            Button(
                onClick = onPagarClick,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Pagar y Guardar",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 4. NUEVO BOTÓN: VER HISTORIAL
            OutlinedButton(
                onClick = onHistorialClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryGreen),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryGreen)
            ) {
                Text(
                    "Ver Pedidos Anteriores",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun obtenerImagenPorNombre(nombreAlbum: String): Int {
    return when {
        nombreAlbum.contains("Showbiz", ignoreCase = true) -> R.drawable.showbiz
        nombreAlbum.contains("Origin", ignoreCase = true) -> R.drawable.origin
        nombreAlbum.contains("Resistance", ignoreCase = true) -> R.drawable.resistance
        nombreAlbum.contains("Hybrid", ignoreCase = true) -> R.drawable.hybrid
        nombreAlbum.contains("Meteora", ignoreCase = true) -> R.drawable.meteora
        nombreAlbum.contains("RECHARGED", ignoreCase = true) -> R.drawable.recharged
        nombreAlbum.contains("AM", ignoreCase = true) -> R.drawable.am
        nombreAlbum.contains("Suck", ignoreCase = true) -> R.drawable.suck
        nombreAlbum.contains("Car", ignoreCase = true) -> R.drawable.car
        nombreAlbum.contains("18 Months", ignoreCase = true) -> R.drawable.months
        nombreAlbum.contains("Motion", ignoreCase = true) -> R.drawable.motion
        nombreAlbum.contains("96 Months", ignoreCase = true) -> R.drawable.cmonths
        nombreAlbum.contains("Parachutes", ignoreCase = true) -> R.drawable.para
        nombreAlbum.contains("Mylo", ignoreCase = true) -> R.drawable.mylo
        nombreAlbum.contains("Abbey", ignoreCase = true) -> R.drawable.abbey
        nombreAlbum.contains("Let It Be", ignoreCase = true) -> R.drawable.let
        nombreAlbum.contains("Help", ignoreCase = true) -> R.drawable.help
        else -> R.drawable.abbey
    }
}