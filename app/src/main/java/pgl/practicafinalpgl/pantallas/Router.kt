package pgl.practicafinalpgl.pantallas

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.katarem.piratify.pantallas.PantallaUser
import pgl.practicafinalpgl.Rutas.Rutas
import pgl.practicafinalpgl.db.DBViewModel

@Composable
fun Router() {
    val navController = rememberNavController()
    val entradaNavActual by navController.currentBackStackEntryAsState()
    val dbViewModel: DBViewModel = viewModel()
    val rutaActual = entradaNavActual?.destination?.route
    dbViewModel.Initialize()
    Surface {
        NavHost(navController = navController, startDestination = Rutas.PantallaPlaylists.ruta) {
            composable(Rutas.PantallaLogin.ruta) {
                PantallaLogin()
            }
            composable(Rutas.PantallaPlaylists.ruta) {
                PantallaPlaylists(navController = navController, viewModel = dbViewModel)
            }
            composable(Rutas.PantallaTesting.ruta){
                PantallaPrueba2(model = null)
            }
            composable(Rutas.PantallaUser.ruta){
                PantallaUser()
            }
            composable(Rutas.PantallaAlbum.ruta + "{albumId}") {
                val albumId = it.arguments?.getString("albumId")
                val albumList by dbViewModel.listaAlbums.collectAsState()
                val album = albumList.find { it.id == albumId }
                PantallaAlbum(album = album!!, navController = navController)
            }

        }
    }
}