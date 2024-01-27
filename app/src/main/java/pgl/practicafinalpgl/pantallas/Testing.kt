package pgl.practicafinalpgl.pantallas

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
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
    startPlayback(LocalContext.current)
}

fun startPlayback(context : Context) {
    val storage = Firebase.storage
    val audioFilePath = "songs/rip_and_tear_doom.mp3"

    val audioReference = storage.reference.child(audioFilePath)

    audioReference.downloadUrl.addOnSuccessListener { uri ->
        val downloadUrl = uri.toString()

        val player = ExoPlayer.Builder(context).build()
        val mediaItem = MediaItem.fromUri(downloadUrl)

        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }.addOnFailureListener {

    }
}
