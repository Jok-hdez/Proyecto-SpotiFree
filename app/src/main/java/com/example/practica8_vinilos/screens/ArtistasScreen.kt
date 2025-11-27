package com.example.practica8_vinilos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practica8_vinilos.R
import com.example.practica8_vinilos.ui.theme.PrimaryGreen
import com.example.practica8_vinilos.ui.theme.SurfaceDark
import com.example.practica8_vinilos.ui.theme.TextGray


val allArtistsList = listOf(
    Artist("The Strokes", R.drawable.strokes),
    Artist("Metallica", R.drawable.metal),
    Artist("Travis Scott", R.drawable.travis),
    Artist("Radiohead", R.drawable.radio),
    Artist("Martin Garrix", R.drawable.martin),
    Artist("Calvin Harris", R.drawable.calvin),
    Artist("Coldplay", R.drawable.cold),
    Artist("Little Jesus", R.drawable.little),
    Artist("Eminem", R.drawable.eminem),
    Artist("Fred again...", R.drawable.fred),
    Artist("Outkast", R.drawable.outkast),
    Artist("2Pac", R.drawable.pac)
)

@Composable
fun ArtistasScreen() {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        SearchBar(
            searchText = searchText,
            onSearchChange = { searchText = it }
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            contentPadding = PaddingValues(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            val filteredArtists = allArtistsList.filter {
                it.name.contains(searchText, ignoreCase = true)
            }

            items(filteredArtists) { artist ->
                ArtistGridItem(artist = artist)
            }
        }
    }
}

@Composable
fun SearchBar(searchText: String, onSearchChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                "Buscar artista...",
                color = TextGray
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = TextGray
            )
        },
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
fun ArtistGridItem(artist: Artist) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = artist.imageRes),
            contentDescription = artist.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.DarkGray)
        )
        Text(
            text = artist.name,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}