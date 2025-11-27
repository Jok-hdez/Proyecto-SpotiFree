package com.example.practica8_vinilos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practica8_vinilos.ui.theme.PrimaryGreen
import com.example.practica8_vinilos.ui.theme.SurfaceDark
import com.example.practica8_vinilos.ui.theme.TextGray
import com.example.practica8_vinilos.R


data class Vinyl(val artist: String, val album: String, val price: String, val imageRes: Int)
data class Artist(val name: String, val imageRes: Int)


val novedadesList = listOf(
    Vinyl("Muse", "Origin Of Symmetry", "$132.00", R.drawable.origin),
    Vinyl("Drake", "Views", "$231.00", R.drawable.views),
    Vinyl("Coldplay", "Mylo Xyloto", "$192.00", R.drawable.mylo),
    Vinyl("Linkin Park", "Hybrid Theory", "$150.00", R.drawable.hybrid)
)


val artistasList = listOf(
    Artist("Tyler The Creator", R.drawable.tyler),
    Artist("Arctic Monkeys", R.drawable.arctic),
    Artist("2Pac", R.drawable.pac),
    Artist("Drake", R.drawable.drake)
)


@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        item {
            SectionTitle(title = "Novedades de hoy", subtitle = "Lo más nuevo en corridos on wax.")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp)
            ) {
                items(novedadesList) { vinyl ->
                    VinylCard(vinyl = vinyl)
                }
            }
        }

        
        item {
            SectionTitle(title = "Artistas Destacados")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp)
            ) {
                items(artistasList) { artist ->
                    ArtistCard(artist = artist)
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String, subtitle: String? = null) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray
            )
        }
    }
}

@Composable
fun VinylCard(vinyl: Vinyl) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark)
    ) {
        Column {
            Image(
                painter = painterResource(id = vinyl.imageRes), // Usa tu R.drawable.nombre_imagen
                contentDescription = vinyl.album,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Color.DarkGray) // Placeholder
            )
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = vinyl.artist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = vinyl.album,
                    fontSize = 12.sp,
                    color = TextGray
                )
                Text(
                    text = vinyl.price,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Button(
                    onClick = { /* No funcional */ },
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

@Composable
fun ArtistCard(artist: Artist) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark)
    ) {
        Column {
            Image(
                painter = painterResource(id = artist.imageRes), // Usa tu R.drawable.nombre_imagen
                contentDescription = artist.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(Color.DarkGray) // Placeholder
            )
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = artist.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Button(
                    onClick = { /* No funcional */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Info", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}