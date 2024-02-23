package pgl.practicafinalpgl.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pgl.practicafinalpgl.Rutas.Rutas
import pgl.practicafinalpgl.model.Album
import pgl.practicafinalpgl.model.Song
import pgl.practicafinalpgl.utils.AppColors

@Composable
fun PantallaAlbum(album: Album, navController: NavController?) {
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier.background(AppColors.negro)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AlbumDescription(album = album)
            Button(
                onClick = { },
                //onClick = { navController?.navigate(Rutas.PantallaReproductor.ruta + "/${album.name}/0/true") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = AppColors.verde
                )
            ) {
                Text(text = "Modo aleatorio")
            }
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .weight(1f), content = {
                itemsIndexed(album.songs) { index, cancion ->
                    CancionItem(
                        index = index,
                        cancion = cancion,
                        onClick = {
                            scope.launch(Dispatchers.IO) {
                                album.songs[index].loadPortrait()
                            }
                            navController?.navigate(Rutas.PantallaReproductor.ruta + "/${album.id}/$index/true")
                        })
                }
            })
            Text(
                //text = "${album.songs.size} canciones - ${duracionTotal(album)}",
                text = "${album.songs.size} canciones",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }

}


@Composable
fun CancionItem(index: Int, cancion: Song, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(35.dp)
            .fillMaxWidth()
            .clickable { onClick() }, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "${index + 1}. ${cancion.name}", color = Color.White, fontSize = 20.sp)
//        Text(text = cancion.duracion, color = Color.White, fontSize = 20.sp)
    }
}


//fun duracionTotal(album: Album): String {
//    val duraciones = arrayListOf<Int>()
//    album.songs.map { cancion -> cancion.duracion }
//        .forEach { duraciones.add(durationToInt(it)) }
//    val duracionAlbum = durationParsed(duraciones.sum())
//    val horas = duracionAlbum.split(":")[0].toInt() / 60
//    val minutos = duracionAlbum.split(":")[1].toInt()
//    return if (horas < 1) "$minutos min" else "$horas h $minutos min"
//}

fun durationToInt(value: String): Int {
    val mins = value.split(":")[0]
    val segs = value.split(":")[1]
    return (mins.toInt() * 60) + segs.toInt()
}


@Composable
fun AlbumDescription(album: Album) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        album.portrait?.let {
            Image(
                bitmap = it.asImageBitmap(), contentDescription = "",
                contentScale = ContentScale.Crop, modifier = Modifier.size(200.dp)
            )
        }
        Column(Modifier.padding(5.dp)) {
            Text(text = album.name, color = Color.White, fontSize = 20.sp)
            Text(text = "Placeholder", color = Color.White, fontSize = 20.sp)
            //Text(text = "${album.artista} - ${album.anio}", color = Color.White, fontSize = 20.sp)
        }
    }
}


//
//@Preview(showBackground = true)
//@Composable
//fun PantallaAlbumPreview() {
//    PantallaAlbum(album = Albums.Muerte, navController = null)
//}
