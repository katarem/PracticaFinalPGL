package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pgl.practicafinalpgl.db.PlayerViewModel
import pgl.practicafinalpgl.model.Album
import pgl.practicafinalpgl.model.Song
import pgl.practicafinalpgl.utils.AppColors
import pgl.practicafinalpgl.R

@Composable
fun PantallaReproductor(
    album: Album,
    index: Int,
    isShuffle: Boolean,
    navController: NavHostController) {
    val model: PlayerViewModel = viewModel()
    val context = LocalContext.current

    val cancionActual = model.currentSong.collectAsState()

    val scope = rememberCoroutineScope()
    var horizontalSwipe = remember { mutableStateOf(false) }
    var verticalSwipe = remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .background(AppColors.negro)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { model.playOrPause() }
                )
            }
            .scrollable(
                state = rememberScrollableState { delta ->
                    if (delta > 0) {
                        if (!verticalSwipe.value){
                            verticalSwipe.value = true
                            scope.launch {
                                navController.popBackStack()
                                delay(1000)
                                verticalSwipe.value = false
                            }
                        }
                    }
                    delta
                },
                orientation = Orientation.Vertical
            )
            .scrollable(
                state = rememberScrollableState { delta ->
                    if (!horizontalSwipe.value) {
                        horizontalSwipe.value = true
                        scope.launch {
                            //if (delta < 0) model.nextSong(context)
                            //else if (delta > 0) model.prevSong(context)
                            delay(1500)
                            horizontalSwipe.value = false
                        }
                    }
                    delta
                },
                orientation = Orientation.Horizontal
            ),
        verticalArrangement = Arrangement.Center
    ) {
        SongInfoText(cancionActual.value)
//        Image(
//            painter = painterResource(id = R.drawable.),
//            contentDescription = "",
//            Modifier
//                .fillMaxWidth()
//                .padding(18.dp)
//                .aspectRatio(1f)
//                .clip(RoundedCornerShape(20.dp)),
//            contentScale = ContentScale.Crop,
//        )
        PlayerControls(model, cancionActual.value, album, index, isShuffle)
    }
}

@Composable
fun SongInfoText(cancionActual: Song) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(text = "Now playing", fontSize = 25.sp, color = Color.White)
        Text(
            text = cancionActual.name + " - Placeholder" ,
            //text = cancionActual.name + " - " + cancionActual.artista,
            fontSize = 25.sp,
            color = Color.White
        )
    }
}

@Composable
fun PlayerControls(
    model: PlayerViewModel,
    cancionActual: Song,
    album: Album,
    index: Int,
    shuffleMode: Boolean
) {

    val context = LocalContext.current
    val isPlaying = model.isPlaying.collectAsState()
    val isShuffle = model.isShuffle.collectAsState()
    val isRepeat = model.isRepeating.collectAsState()
    var playIcon = R.drawable.pause
    var repeatIcon = R.drawable.repeat
    var shuffleIcon = R.drawable.shuffle

    if (isRepeat.value) repeatIcon = R.drawable.activated_repeat
    if (isShuffle.value) shuffleIcon = R.drawable.activated_shuffle
    if (!isPlaying.value) playIcon = R.drawable.play

    LaunchedEffect(Unit) {
        //setteamos el album que recibimos
        model.changeAlbum(album, index, shuffleMode)
        model.createExoPlayer(context)
        //model.playSong(context)
    }

    val currentSong = model.currentSong.collectAsState()

    LaunchedEffect(currentSong.value) {
        //actualizamos el icono de play/pause
        playIcon = if (isPlaying.value) R.drawable.pause else R.drawable.play
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Column {
            SliderView(cancionActual)
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { model.changeShuffleState(context, !isShuffle.value) }) {
                    Image(painter = painterResource(id = shuffleIcon), contentDescription = "")
                }
                TextButton(
                    onClick = {  }
                    //onClick = { model.prevSong(context) }
                ) {
                    Image(painter = painterResource(id = R.drawable.prev), contentDescription = "")
                }
                Button(
                    onClick = { model.playOrPause() }, modifier = Modifier
                        .size(70.dp)
                        .clip(
                            CircleShape
                        ), colors = ButtonDefaults.buttonColors(containerColor = AppColors.verde)
                ) {
                    Image(
                        painter = painterResource(id = playIcon),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                TextButton(
                    onClick = {  }
                    //onClick = { model.nextSong(context) }
                ) {
                    Image(painter = painterResource(id = R.drawable.next), contentDescription = "")
                }
                TextButton(onClick = { model.changeRepeatingState(!isRepeat.value) }) {
                    Image(painter = painterResource(id = repeatIcon), contentDescription = "")
                }
            }

        }
    }
}

fun durationParsed(tiempo: Int): String {
    val minutos = tiempo / 60
    val segundos = tiempo - (minutos * 60)
    var minutosString = "" + minutos
    var segundosString = "" + segundos
    if (segundos < 10) segundosString = "0$segundosString"
    if (minutos < 10) minutosString = "0$minutosString"
    return "$minutosString:$segundosString"
}


@Composable
fun SliderView(cancionActual: Song) {
    val model: PlayerViewModel = viewModel()
    val duracion = model.duracion.collectAsState()
    val progreso = model.progreso.collectAsState()

    Column(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Slider(
            value = progreso.value.toFloat(),
            onValueChange = { model.changeProgreso(it.toInt()) },
            valueRange = 0f..duracion.value.toFloat(),
            colors = SliderDefaults.colors(thumbColor = AppColors.verde)
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = durationParsed((progreso.value / 1000)), color = Color.White)
            Text(text = durationParsed(duracion.value / 1000), color = Color.White)
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PantallaPrincipalPreview() {
//    PantallaReproductor(album = Albums.Nightmare, 0, false, navController = NavHostController(LocalContext.current))
//}