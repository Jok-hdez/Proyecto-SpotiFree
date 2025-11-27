package com.example.practica8_vinilos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
/* =========================================================== */
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practica8_vinilos.screens.*
import com.example.practica8_vinilos.ui.theme.Practica8_VinilosTheme
import kotlinx.coroutines.launch


import androidx.room.Room
import com.example.practica8_vinilos.database.VinilosDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- 2. INICIALIZAMOS LA BASE DE DATOS ---

        val db = Room.databaseBuilder(
            applicationContext,
            VinilosDatabase::class.java,
            "vinilos_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        // --- 3. INICIALIZAMOS EL VIEWMODEL CONECTADO A LA BD ---
        val viewModel = VinilosViewModel(db.pedidoDao())

        setContent {
            Practica8_VinilosTheme {
                // Pasamos el viewModel a la navegación
                AppNavegacion(viewModel)
            }
        }
    }
}


data class NavItem(val route: String, val label: String, val icon: ImageVector)

val navItems = listOf(
    NavItem("inicio", "Inicio", Icons.Default.Home),
    NavItem("artistas", "Artistas", Icons.Default.Person),
    NavItem("catalogo", "Catálogo", Icons.Default.List)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// RECIBIMOS EL VIEWMODEL COMO PARAMETRO ---
fun AppNavegacion(viewModel: VinilosViewModel) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                Text(
                    "Menú",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Divider(modifier = Modifier.padding(bottom = 12.dp))

                // Items de navegación
                navItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = item.label, tint = MaterialTheme.colorScheme.onSurface) },
                        label = { Text(item.label, color = MaterialTheme.colorScheme.onSurface) },
                        selected = false,
                        onClick = {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "SpotiFree",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            navController.navigate("carrito")
                        }) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Ver carrito",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        ) { paddingValues ->
            // Contenedor de Navegación
            NavHost(
                navController = navController,
                startDestination = "inicio",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable("inicio") { HomeScreen() }

                composable("artistas") { ArtistasScreen() }

                composable("catalogo") {
                    CatalogoScreen(viewModel = viewModel)
                }

                composable("carrito") {
                    CarritoScreen(
                        viewModel = viewModel,
                        onVerHistorialClick = { navController.navigate("historial") } // <--- CAMBIO AQUÍ
                    )
                }

                composable("historial") {
                    HistorialScreen(viewModel = viewModel)
                }
            }
        }
    }
}