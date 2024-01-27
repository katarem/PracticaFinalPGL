package pgl.practicafinalpgl.pantallas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pgl.practicafinalpgl.db.DBViewModel
import pgl.practicafinalpgl.ui.theme.PracticaFinalPGLTheme

/***
 * PANTALLA PARA PRUEBAS
 */


class Testing : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticaFinalPGLTheme {
                PantallaPrueba()
            }
        }
    }
}


@Composable
fun PantallaPrueba(){
    Firebase.auth.signOut()
    Text(text = "holamundo")
}