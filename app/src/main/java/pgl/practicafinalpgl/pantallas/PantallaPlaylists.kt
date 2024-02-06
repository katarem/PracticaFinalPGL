package pgl.practicafinalpgl.pantallas

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pgl.practicafinalpgl.R
import pgl.practicafinalpgl.Rutas.Rutas
import pgl.practicafinalpgl.db.DBViewModel
import pgl.practicafinalpgl.db.Repository
import pgl.practicafinalpgl.model.Album
import pgl.practicafinalpgl.utils.AppColors

@Composable
fun PantallaPlaylists(navController: NavController?) {

    val dbViewModel: DBViewModel = viewModel()
    val albums = dbViewModel.albumRepository.collectAsState()

    DisposableEffect(Unit) {
        dbViewModel.crearListenerSongs()
        dbViewModel.crearListenerAlbums()
        onDispose {
            dbViewModel.removeListener()
        }
    }

    Log.d("CRIS_DEBUG","A PARTIR DE AQUÍ VEMOS LOS ÁLBUMES")
    Log.d("CRIS_DEBUG",albums.value.repository.size.toString())



    Box(modifier = Modifier.background(AppColors.negro)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                text = "Últimos álbumes que te pueden gustar",
                color = Color.White,
                fontSize = 20.sp
            )
            LazyRow(
                content = {
                    items(albums.value.getAll()) {
                        AlbumItem(
                            album = it,
                            { navController?.navigate(Rutas.PantallaAlbum.ruta + "/${it.name}") })
                    }
                }, modifier = Modifier
                    .padding(10.dp), horizontalArrangement = Arrangement.SpaceAround
            )
            Text(
                text = "Sigue escuchando tu música favorita",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun AlbumItem(album: Album, onClick: () -> Unit) {
    Card(modifier = Modifier
        .size(150.dp)
        .padding(5.dp)
        .clickable { onClick() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(AppColors.verde)
        ) {
            Image(
                painter = painterResource(id = R.drawable.maw),
                contentDescription = album.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = album.name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPlaylistsPreview() {
    PantallaPlaylists(null)
}