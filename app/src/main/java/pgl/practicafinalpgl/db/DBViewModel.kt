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
import pgl.practicafinalpgl.model.login.Admin
import pgl.practicafinalpgl.model.login.Artist
import pgl.practicafinalpgl.model.login.Permissions
import pgl.practicafinalpgl.model.login.User
import pgl.practicafinalpgl.model.login.UserType

class DBViewModel : ViewModel() {

    val conexion = FirebaseFirestore.getInstance()
    private lateinit var listenerReg: ListenerRegistration

    // de momento lo vamos a poner así a ver como funciona

    private lateinit var listenerSongs: ListenerRegistration
    private lateinit var listenerAlbums: ListenerRegistration
    private lateinit var listenerPlaylists: ListenerRegistration
    private lateinit var listenerUsers: ListenerRegistration


    private var _listaAlbums = MutableStateFlow(mutableStateListOf<Album>())
    val listaAlbums = _listaAlbums.asStateFlow()

    private var _listaCanciones = MutableStateFlow(mutableStateListOf<Song>())
    val listaCanciones = _listaCanciones.asStateFlow()

    private var _listaPlaylists = MutableStateFlow(mutableStateListOf<Playlist>())
    val listaPlaylists = _listaPlaylists.asStateFlow()

    private var _listaUsers = MutableStateFlow(mutableStateListOf<UserType>())
    val listaUsers = _listaUsers.asStateFlow()

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
                            val newEntity = Song.toObject(cambios.document)
                            _listaCanciones.value.remove(newEntity)
                        }
                    }
                }
            }
        }
    }
    fun crearListenerPlaylists() {
        listenerSongs = conexion.collection("playlist").addSnapshotListener { datos, error ->
            if (error == null) {
                datos?.documentChanges?.forEach { cambios ->
                    when (cambios.type) {
                        DocumentChange.Type.ADDED -> {
                            viewModelScope.launch(Dispatchers.IO) {
                                val playlist = Playlist.toObject(cambios.document)
                                _listaPlaylists.value.add(playlist)
                            }
                        }

                        DocumentChange.Type.MODIFIED -> {
                            val newPlaylist = Playlist.toObject(cambios.document)
                            val oldPlaylist = _listaPlaylists.value.filter { p -> p.id == newPlaylist.id }.first()
                            _listaPlaylists.value.remove(oldPlaylist)
                            _listaPlaylists.value.add(newPlaylist)
                        }

                        DocumentChange.Type.REMOVED -> {
                            val newPlaylist = Playlist.toObject(cambios.document)
                            _listaPlaylists.value.remove(newPlaylist)
                        }
                    }
                }
            }
        }
    }

    fun crearListenerUsers() {
        listenerSongs = conexion.collection("user").addSnapshotListener { datos, error ->
            if (error == null) {
                datos?.documentChanges?.forEach { cambios ->
                    when (cambios.type) {
                        DocumentChange.Type.ADDED -> {
                            viewModelScope.launch(Dispatchers.IO) {
                                var user = UserType.toObject(cambios.document)
                                user = when(user.permissions){
                                    Permissions.USER -> user as User
                                    Permissions.ADMIN -> user as Admin
                                    Permissions.ARTIST -> user as Artist
                                }
                                _listaUsers.value.add(user)
                            }
                        }

                        DocumentChange.Type.MODIFIED -> {
                            var newUser = UserType.toObject(cambios.document)
                            newUser = when(newUser.permissions){
                                Permissions.USER -> newUser as User
                                Permissions.ADMIN -> newUser as Admin
                                Permissions.ARTIST -> newUser as Artist
                            }
                            val oldUser = _listaUsers.value.filter { u -> u.id == newUser.id }.first()
                            _listaUsers.value.remove(oldUser)
                            _listaUsers.value.add(newUser)
                        }

                        DocumentChange.Type.REMOVED -> {
                            var user = UserType.toObject(cambios.document)
                            user = when(user.permissions){
                                Permissions.USER -> user as User
                                Permissions.ADMIN -> user as Admin
                                Permissions.ARTIST -> user as Artist
                            }
                            _listaUsers.value.remove(user)
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
            crearListenerPlaylists()
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

    fun removeListenerPlaylists(){
        listenerPlaylists.remove()
    }

    fun removeListenerUsers(){
        listenerUsers.remove()
    }

    fun removeListenerSongs() {
        listenerSongs.remove()
    }



    fun removeListener() {
        listenerReg.remove()
    }
// TODO AQUI TE DEJO EL CÓDIGO QUE TENÍA, DE TODAS FORMAS TIENES EL PROYECTO DE GRIFA, SORRY NO ME DA MÁS TIEMPO AHORA MISMO

//    fun addSong(){
//        var nueva = Papa(marca = "munchitos",precio = 7.2,sabor = "original")
//        conexion.collection("song").add(nueva)
//    }
//
//    fun modificarPapa(papaAcambiar : Papa){
//        conexion.collection("song")
//            .document(papaAcambiar.idPapa).set(papaAcambiar)
//    }
//
//    fun borrarPapa(papaAborrar: Papa){
//        conexion.collection("song").document(papaAborrar.idPapa).delete()
//    }

}