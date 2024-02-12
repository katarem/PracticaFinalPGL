package pgl.practicafinalpgl.db

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.local.ReferenceSet
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import pgl.practicafinalpgl.model.Album
import pgl.practicafinalpgl.model.Playlist
import pgl.practicafinalpgl.model.Song
import kotlin.collections.ArrayList

class DBViewModel : ViewModel(){

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

    fun crearListenerSongs(){
        listenerSongs = conexion.collection("song").addSnapshotListener{
            datos,error ->
                if(error==null){
                    datos?.documentChanges?.forEach { cambio ->
                        when(cambio.type){
                            DocumentChange.Type.ADDED -> {
                                Log.d("CANCION",cambio.document.toObject<Song>().name)
                                _songRepository.value.insert(cambio.document.toObject<Song>())
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val newEntity = cambio.document.toObject<Song>()
                                _songRepository.value.update(newEntity)
                            }
                            DocumentChange.Type.REMOVED -> {
                                val newEntity = cambio.document.toObject<Song>()
                                _songRepository.value.remove(newEntity)
                            }
                        }
                    }
                }
        }

    }
//    @Composable
//    fun Initialize(){
//        DisposableEffect(Unit) {
//            crearListenerSongs()
//                onDispose {
//                    removeListenerSongs()
//                }
//        }
//    }


    fun crearListenerAlbums(){
        listenerAlbums = conexion.collection("Album").addSnapshotListener{
                snapshot,error ->
            if(error==null){
                snapshot?.documentChanges?.forEach { cambios ->
                    when(cambios.type){
                        DocumentChange.Type.ADDED -> {
                            var obtained = cambios.document//Could not deserialize object. Can't convert object of type com.google.firebase.firestore.DocumentReference to type pgl.practicafinalpgl.model.Song (found in field 'songs.[0]'
                            var songRefs = cambios.document["songs"] as ArrayList<DocumentReference>
                            var canciones = songRefs.map { it.get() }.toList() as ArrayList<Song>
//                            obtained.songs = canciones
//                            _albumRepository.value.insert(obtained)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val newEntity = cambios.document.toObject<Album>()
                            _albumRepository.value.update(newEntity)
                        }
                        DocumentChange.Type.REMOVED -> {
                            val newEntity = cambios.document.toObject<Album>()
                            _albumRepository.value.repository.remove(newEntity)
                        }
                    }
                }
            }
        }

    }





    fun removeListenerAlbums(){ listenerAlbums.remove() }

    fun removeListenerSongs(){ listenerSongs.remove() }

    fun removeListener(){ listenerReg.remove() }

    fun addAlbum(album: Album){ _albumRepository.value.insert(album) }
    fun modifyAlbum(album: Album){ _albumRepository.value.update(album) }
    fun removeAlbum(album: Album): Boolean{ return _albumRepository.value.remove(album)}
    fun getAllAlbum(): List<Album>{ return _albumRepository.value.getAll()}
    fun getAlbumById(id: String): Album{ return _albumRepository.value.getById(Album(id))}
    fun addSong(song: Song){ _songRepository.value.insert(song) }
    fun modifySong(song: Song){ _songRepository.value.update(song) }
    fun removeSong(song: Song): Boolean{ return _songRepository.value.remove(song) }
    fun getAllSong(): List<Song> { return _songRepository.value.getAll() }
    fun getSongById(id: String): Song { return _songRepository.value.getById(Song(id))}
    fun addPlaylist(playlist: Playlist){ _playlistRepository.value.insert(playlist) }
    fun modifyPlaylist(playlist: Playlist){ _playlistRepository.value.update(playlist) }
    fun removePlaylist(playlist: Playlist): Boolean { return _playlistRepository.value.remove(playlist)}
    fun getAllPlaylist(): List<Playlist> { return _playlistRepository.value.getAll() }
    fun getPlaylistById(id: String): Playlist{ return _playlistRepository.value.getById(Playlist(id))}

}