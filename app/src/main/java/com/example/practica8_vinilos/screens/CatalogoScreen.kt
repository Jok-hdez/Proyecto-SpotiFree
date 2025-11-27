package com.example.practica8_vinilos.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.practica8_vinilos.ui.theme.*

import com.example.practica8_vinilos.VinilosViewModel



data class ArtistSectionData(val artistName: String, val albums: List<Vinyl>)


val catalogoData = listOf(
    ArtistSectionData(
        "Muse",
        listOf(
            Vinyl("Muse", "Showbiz", "$132.00", R.drawable.showbiz),
            Vinyl("Muse", "Origin Of Symmetry", "$250.00", R.drawable.origin),
            Vinyl("Muse", "Resistance", "$199.00", R.drawable.resistance)
        )
    ),
    ArtistSectionData(
        "Linkin Park",
        listOf(
            Vinyl("Linkin Park", "Hybrid Theory", "$192.00", R.drawable.hybrid),
            Vinyl("Linkin Park", "Meteora", "$180.00", R.drawable.meteora),
            Vinyl("Linkin Park", "RECHARGED", "$175.00", R.drawable.recharged)
        )
    ),
    ArtistSectionData(
        "Arctic Monkeys",
        listOf(
            Vinyl("Arctic Monkeys", "AM", "$210.00", R.drawable.am),
            Vinyl("Arctic Monkeys", "Suck It And See", "$200.00", R.drawable.suck),
            Vinyl("Arctic Monkeys", "The Car", "$190.00", R.drawable.car)
        )
    ),
    ArtistSectionData(
        "Calvin Harris",
        listOf(
            Vinyl("Calvin Harris", "18 Months", "$180.00", R.drawable.months),
            Vinyl("Calvin Harris", "Motion", "$231.00", R.drawable.motion),
            Vinyl("Calvin Harris", "96 Months", "$195.00", R.drawable.cmonths)
        )
    ),
    ArtistSectionData(
        "Coldplay",
        listOf(
            Vinyl("Coldplay", "Parachutes", "$150.00", R.drawable.para),
            Vinyl("Coldplay", "Mylo Xyloto", "$160.00", R.drawable.mylo)
        )
    ),
    ArtistSectionData(
        "The Beatles",
        listOf(
            Vinyl("The Beatles", "Abbey Road", "$230.00", R.drawable.abbey),
            Vinyl("The Beatles", "Let It Be", "$200.00", R.drawable.let),
            Vinyl("The Beatles", "Help!", "$195.00", R.drawable.help)
        )
    )
)

@Composable
fun CatalogoScreen(viewModel: VinilosViewModel) {
    var searchText by remember { mutableStateOf("") }
    val filters = listOf("Género", "Nuevos", "Precio")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- Barra de Búsqueda ---
        item {
            SearchBar(
                searchText = searchText,
                onSearchChange = { searchText = it },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // --- Botones de Filtro ---
        item {
            FilterButtonRow(filters = filters)
        }

        // --- Secciones de Artistas ---
        val filteredList = catalogoData.filter { section ->
            section.artistName.contains(searchText, ignoreCase = true) ||
                    section.albums.any { it.album.contains(searchText, ignoreCase = true) }
        }

        items(filteredList) { section ->
            // Pasamos el viewModel hacia abajo
            ArtistAlbumSection(section = section, viewModel = viewModel)
        }
    }
}

@Composable
fun SearchBar(
    searchText: String,
    onSearchChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Buscar álbum o artista...", color = TextGray) },
        leadingIcon = { Icon(Icons.Default.Search, "Buscar", tint = TextGray) },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SurfaceDark,
            unfocusedContainerColor = SurfaceDark,
            disabledContainerColor = SurfaceDark,
            focusedBorderColor = PrimaryGreen,
            unfocusedBorderColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = PrimaryGreen
        ),
        singleLine = true
    )
}

@Composable
fun FilterButtonRow(filters: List<String>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters) { filter ->
            Button(
                onClick = { /* No funcional por ahora */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGreen,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(filter, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ArtistAlbumSection(section: ArtistSectionData, viewModel: VinilosViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = section.artistName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Ver todos >",
                color = PrimaryGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(section.albums) { album ->
                VinylCard(vinyl = album, onAgregarClick = {
                    // --- AQUÍ OCURRE LA MAGIA DE LA BD ---
                    // Convertimos "$132.00" a 132.0 (Double)
                    val precioLimpio = album.price.replace("$", "").toDoubleOrNull() ?: 0.0

                    // Llamamos al ViewModel para guardar
                    viewModel.agregarAlCarrito(album.album, album.artist, precioLimpio)
                })
            }
        }
    }
}

@Composable
fun VinylCard(vinyl: Vinyl, onAgregarClick: () -> Unit) { // Recibe la función click
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark)
    ) {
        Column {
            Image(
                painter = painterResource(id = vinyl.imageRes),
                contentDescription = vinyl.album,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Color.DarkGray)
            )
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = vinyl.artist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )
                Text(
                    text = vinyl.album,
                    fontSize = 12.sp,
                    color = TextGray,
                    maxLines = 1
                )
                Text(
                    text = vinyl.price,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 8.dp)
                )

                // BOTÓN AGREGAR
                Button(
                    onClick = onAgregarClick, // <--- Usamos la función que viene de arriba
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Agregar", color = Color.Black, fontSize = 12.sp)
                }
            }
        }
    }
}