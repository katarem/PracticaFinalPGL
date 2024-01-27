package pgl.practicafinalpgl.pantallas

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.katarem.piratify.pantallas.PantallaLogin
import pgl.practicafinalpgl.Rutas.Rutas
import pgl.practicafinalpgl.utils.loginUser

@Composable
fun Router() {
    val navController = rememberNavController()
    val entradaNavActual by navController.currentBackStackEntryAsState()

    val rutaActual = entradaNavActual?.destination?.route

    Surface {
        NavHost(navController = navController, startDestination = Rutas.PantallaLogin.ruta) {
            composable(Rutas.PantallaLogin.ruta) {
                PantallaLogin()
            }
        }
    }
}