package pgl.practicafinalpgl.db

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pgl.practicafinalpgl.model.Album
import pgl.practicafinalpgl.model.Playlist
import pgl.practicafinalpgl.model.Song

class DBViewModel : ViewModel() {

    val conexion = FirebaseFirestore.getInstance()
    private lateinit var listenerReg: ListenerRegistration

    // de momento lo vamos a poner así a ver como funciona

    private lateinit var listenerSongs: ListenerRegistration
    private lateinit var listenerAlbums: ListenerRegistration

    private var _listaAlbums = MutableStateFlow(mutableStateListOf<Album>())
    val listaAlbums = _listaAlbums.asStateFlow()

    private var _listaCanciones = MutableStateFlow(mutableStateListOf<Song>())
    val listaCanciones = _listaCanciones.asStateFlow()

    private var _playlistRepository = MutableStateFlow(PlaylistRepository())
    val playlistRepository = _playlistRepository.asStateFlow()


    fun crearListenerSongs() {
        listenerSongs = conexion.collection("song").addSnapshotListener { datos, error ->
            if (error == null) {
                datos?.documentChanges?.forEach { cambios ->
                    when (cambios.type) {
                        DocumentChange.Type.ADDED -> {
                            viewModelScope.launch(Dispatchers.IO) {
                                val song = Song.toObject(cambios.document)
                                _listaCanciones.value.add(song)
                            }
                        }

                        DocumentChange.Type.MODIFIED -> {
                            val newEntity = cambios.document.toObject<Song>()
                            val oldSong = _listaCanciones.value.filter { s -> s.id == newEntity.id }.first()
                            _listaCanciones.value.remove(oldSong)
                            _listaCanciones.value.add(newEntity)
                        }

                        DocumentChange.Type.REMOVED -> {
                            val newEntity = cambios.document.toObject<Song>()
                            _listaCanciones.value.remove(newEntity)
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun Initialize(){
        DisposableEffect(Unit) {
            crearListenerSongs()
            crearListenerAlbums()
            onDispose {
                removeListenerSongs()
                removeListenerAlbums()
            }
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun crearListenerAlbums() {
        listenerAlbums = conexion.collection("Album").addSnapshotListener { snapshot, error ->
            if (error == null) {
                snapshot?.documentChanges?.forEach { cambios ->
                    when (cambios.type) {
                        DocumentChange.Type.ADDED -> {
                            viewModelScope.launch(Dispatchers.IO) {
                                val obtained = Album.toObject(cambios.document)
                                _listaAlbums.value.add(obtained)
                            }
                        }

                        DocumentChange.Type.MODIFIED -> {
                            val newEntity = cambios.document.toObject<Album>()
                            val oldAlbum = _listaAlbums.value.filter{ a -> a.id == newEntity.id }.first()
                            _listaAlbums.value.remove(oldAlbum)
                            _listaAlbums.value.add(newEntity)
                        }

                        DocumentChange.Type.REMOVED -> {
                            val newEntity = cambios.document.toObject<Album>()
                            _listaAlbums.value.remove(newEntity)
                        }

                        else -> {
                            Log.d("CHRIS_DEBUG", "No se ha podido realizar la operación")
                        }
                    }
                }
            }
        }
    }


    fun removeListenerAlbums() {
        listenerAlbums.remove()
    }

    fun removeListenerSongs() {
        listenerSongs.remove()
    }

    fun removeListener() {
        listenerReg.remove()
    }

    fun addPlaylist(playlist: Playlist) {
        _playlistRepository.value.insert(playlist)
    }

    fun modifyPlaylist(playlist: Playlist) {
        _playlistRepository.value.update(playlist)
    }

    fun removePlaylist(playlist: Playlist): Boolean {
        return _playlistRepository.value.remove(playlist)
    }

    fun getAllPlaylist(): List<Playlist> {
        return _playlistRepository.value.getAll()
    }

    fun getPlaylistById(id: String): Playlist {
        return _playlistRepository.value.getById(Playlist(id))
    }

}