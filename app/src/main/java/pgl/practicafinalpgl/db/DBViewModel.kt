package pgl.practicafinalpgl.db

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.DelicateCoroutinesApi
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
    private var _albumRepository = MutableStateFlow(AlbumRepository())
    val albumRepository = _albumRepository.asStateFlow()

    private var _songRepository = MutableStateFlow(SongRepository())
    val songRepository = _songRepository.asStateFlow()

    private var _playlistRepository = MutableStateFlow(PlaylistRepository())
    val playlistRepository = _playlistRepository.asStateFlow()


    fun crearListenerSongs() {
        listenerSongs = conexion.collection("song").addSnapshotListener { datos, error ->
            if (error == null) {
                datos?.documentChanges?.forEach { cambios ->
                    when (cambios.type) {
                        DocumentChange.Type.ADDED -> {
                            GlobalScope.launch {
                                val song = Song.toObject(cambios.document)
                                _songRepository.value.update(song)
                                Log.d("CHRIS_DEBUG", "Songs: ${_songRepository.value.getAll()}")
                            }
                        }

                        DocumentChange.Type.MODIFIED -> {
                            val newEntity = cambios.document.toObject<Song>()
                            _songRepository.value.update(newEntity)
                        }

                        DocumentChange.Type.REMOVED -> {
                            val newEntity = cambios.document.toObject<Song>()
                            _songRepository.value.remove(newEntity)
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
                            GlobalScope.launch {
                                val obtained = Album.toObject(cambios.document)
                                val currentRepo = _albumRepository.value
                                currentRepo.insert(obtained)
                                _albumRepository.value = currentRepo
                                Log.d("CHRIS_DEBUG", "Albums: ${_albumRepository?.value?.getAll()}")
                            }
                        }

                        DocumentChange.Type.MODIFIED -> {
                            val newEntity = cambios.document.toObject<Album>()
                            val currentRepo = _albumRepository.value
                            currentRepo.update(newEntity)
                            _albumRepository.value = currentRepo
                        }

                        DocumentChange.Type.REMOVED -> {
                            val newEntity = cambios.document.toObject<Album>()
                            val currentRepo = _albumRepository.value
                            currentRepo.remove(newEntity)
                            _albumRepository.value = currentRepo
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

    fun addAlbum(album: Album) {
        _albumRepository.value.insert(album)
    }

    fun modifyAlbum(album: Album) {
        _albumRepository.value.update(album)
    }

    fun removeAlbum(album: Album): Boolean {
        return _albumRepository.value.remove(album)
    }

    fun getAllAlbum(): List<Album> {
        return _albumRepository.value.getAll()
    }

    fun getAlbumById(id: String): Album {
        return _albumRepository.value.getById(Album(id))
    }

    fun addSong(song: Song) {
        _songRepository.value.insert(song)
    }

    fun modifySong(song: Song) {
        _songRepository.value.update(song)
    }

    fun removeSong(song: Song): Boolean {
        return _songRepository.value.remove(song)
    }

    fun getAllSong(): List<Song> {
        return _songRepository.value.getAll()
    }

    fun getSongById(id: String): Song {
        return _songRepository.value.getById(Song(id))
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