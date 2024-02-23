package pgl.practicafinalpgl.db

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.util.Log
import androidx.annotation.AnyRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import pgl.practicafinalpgl.model.Album
import pgl.practicafinalpgl.model.Song

class PlayerViewModel(album: Album, index: Int, isShuffle: Boolean) : ViewModel() {

    //album o playlist elegido
    private var _album = MutableStateFlow(album)
    val album = _album.asStateFlow()
    //Lista de canciones
    private var _canciones = MutableStateFlow(_album.value.songs)
    val canciones = _canciones.asStateFlow()
    //index de la cancion
    private val _index = MutableStateFlow(0)

    val index = _index.asStateFlow()
    //Player
    private val _exoPlayer: MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()

    //Canción actual
    private var _currentSong = MutableStateFlow(canciones.value.get(index))
    val currentSong = _currentSong.asStateFlow()

    //Duración total
    private var _duracion = MutableStateFlow(0)
    val duracion = _duracion.asStateFlow()

    //Progreso actual de la canción
    private var _progreso = MutableStateFlow(0)
    val progreso = _progreso.asStateFlow()

    //Está Repeat
    private var _isRepeating = MutableStateFlow(false)
    val isRepeating = _isRepeating.asStateFlow()

    //Está Shuffled
    private var _isShuffle = MutableStateFlow(isShuffle)
    val isShuffle = _isShuffle.asStateFlow()

    //Reproduciendo o no
    private var _isPlaying = MutableStateFlow(true)
    var isPlaying = _isPlaying.asStateFlow()

    //Cambiamos de Album. Sirve para poder llamar reproductor desde playlist y albumes
    fun changeAlbum(newAlbum: Album, newIndex: Int, shuffleMode: Boolean) {
        _album.value = newAlbum
        if(shuffleMode) {
            _isShuffle.value = true
            val shuffled = newAlbum.songs.shuffled()
            _canciones.value = shuffled as ArrayList<Song>
        }
        else _canciones.value = newAlbum.songs
        _index.value = newIndex
        _currentSong.value = _canciones.value[_index.value]
    }

    //Repite o no
    fun changeRepeatingState(newState: Boolean){
        _isRepeating.value = newState
    }
    //Cambiar si mezclamos o no las canciones
    fun changeShuffleState(context: Context, newState: Boolean){
        _isShuffle.value = newState
        if(_isShuffle.value) {
            _index.value = 0
            _canciones.value = _canciones.value.shuffled() as ArrayList<Song>
        }
        else{
            _canciones.value = _album.value.songs
        }
    }
    //Crear Player
    fun createExoPlayer(context: Context){
        _exoPlayer.value = ExoPlayer.Builder(context).build()
        exoPlayer.value?.prepare()
    }

    fun playSong(context: Context, audioFilePath: String) {
        Firebase.storage.getReferenceFromUrl(audioFilePath).downloadUrl.addOnSuccessListener { uri ->
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(uri.toString()))
                prepare()
                play()
                addListener(getPlayerListener(context))
                _exoPlayer.value = this
            }
            Log.d("CHRIS_DEBUG", "Cancion descargada")
        }.addOnFailureListener {
            Log.d("CHRIS_DEBUG", "Error al descargar la cancion")
        }

    }

    private fun getPlayerListener(context: Context) = object: Player.Listener{
        override fun onPlaybackStateChanged(playbackState: Int) {
            when(playbackState){
                Player.STATE_READY -> {
                    _duracion.value = _exoPlayer.value!!.duration.toInt()
                    viewModelScope.launch {
                        while(isActive){
                            _progreso.value = _exoPlayer.value!!.currentPosition.toInt()
                            delay(1000)
                        }
                    }
                }
                //Player.STATE_BUFFERING->{}//nada basicamente
                //Player.STATE_ENDED -> nextSong(context)
                //Player.STATE_IDLE->{}
            }
        }
    }

    // Este método se llama cuando el VM se destruya.
    override fun onCleared() {
        _exoPlayer.value!!.release()
        super.onCleared()
    }
    //Pausar o reproducir
    fun playOrPause() {
        if (_exoPlayer.value!!.isPlaying){
            _exoPlayer.value!!.pause()
            _isPlaying.value = false
        }else {
            _exoPlayer.value!!.play()
            _isPlaying.value = true
        }
    }
    //Cambiar canción XD
//    fun cambiarCancion(context: Context) {
//        _exoPlayer.value!!.stop()
//        _exoPlayer.value!!.clearMediaItems()
//        _exoPlayer.value!!.setMediaItem(MediaItem.fromUri(obtenerRuta(context, _currentSong.value.media)))
//        _exoPlayer.value!!.prepare()
//        _exoPlayer.value!!.playWhenReady = true
//    }
    //Siguiente canción
//    fun nextSong(context: Context){
//        if(_index.value==canciones.value.size-1 && isRepeating.value){
//            _index.value = 0
//            _currentSong.value = canciones.value[_index.value]
//            cambiarCancion(context)
//        }
//        else if(_index.value < canciones.value.size-1){
//            _index.value += 1
//            _currentSong.value = canciones.value[_index.value]
//            cambiarCancion(context)
//        }
//    }
//    //Canción previa
//    fun prevSong(context: Context){
//        if(_index.value==0 && isRepeating.value){
//            _index.value = canciones.value.size-1
//            _currentSong.value = canciones.value[_index.value]
//            cambiarCancion(context)
//        }
//        else if(_index.value > 0){
//            _index.value -= 1
//            _currentSong.value = canciones.value[_index.value]
//            cambiarCancion(context)
//        }
//    }

    fun changeProgreso(progreso: Int){
        _exoPlayer.value!!.seekTo(progreso.toLong())
    }

    // Funcion auxiliar que devuelve la ruta de un fichero a partir de su ID
    @Throws(Resources.NotFoundException::class)
    fun obtenerRuta(context: Context, @AnyRes resId: Int): Uri {
        val res: Resources = context.resources
        return Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + res.getResourcePackageName(resId)
                    + '/' + res.getResourceTypeName(resId)
                    + '/' + res.getResourceEntryName(resId)
        )
    }


}