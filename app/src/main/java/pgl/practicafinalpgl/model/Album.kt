package pgl.practicafinalpgl.model

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.util.ArrayList

class Album() {
    var id: String? = ""
    var name: String = ""
    var songs: ArrayList<Song> = ArrayList()
    var releaseDate: String = ""
    var artistId: String = ""
    var portait: String = ""
    constructor(id: String = "") : this() {
        this.id = id
    }

    override fun toString(): String {
        return "Album(id='$id', name='$name', releaseDate='$releaseDate', artistId='$artistId', portrait='$portait', songs=$songs)"
    }

    companion object {
        suspend fun toObject(document: DocumentSnapshot): Album {
            val album = Album()
            album.id = document.getString("id") ?: ""
            album.name = document.getString("name") ?: ""
            album.releaseDate = document.getString("releaseDate") ?: ""
            album.artistId = document.getString("artistId") ?: ""
            album.portait = document.getString("portrait") ?: ""

            val songRefs = document.get("songs") as List<DocumentReference>
            album.songs = ArrayList(fetchSongs(songRefs))
            return album
        }

        private suspend fun fetchSongs(songRefs: List<DocumentReference>): List<Song> {
            return songRefs.map { ref ->
                ref.get().await().toObject<Song>()!!
            }
        }
    }
}