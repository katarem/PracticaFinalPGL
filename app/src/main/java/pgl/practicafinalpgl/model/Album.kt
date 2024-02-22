package pgl.practicafinalpgl.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class Album() {
    var id: String? = ""
    var name: String = ""
    var songs: ArrayList<Song> = ArrayList()
    var releaseDate: String = ""
    var artistId: String = ""
    var portraitURL: String = ""
    var portrait: Bitmap? = null

    constructor(id: String = "") : this() {
        this.id = id
    }

    override fun toString(): String {
        return "Album(id='$id', name='$name', releaseDate='$releaseDate', artistId='$artistId', portrait='$portraitURL', songs=$songs)"
    }

    companion object {
        suspend fun toObject(document: DocumentSnapshot): Album {
            val album = Album()
            album.id = document.getString("id") ?: ""
            album.name = document.getString("name") ?: ""
            album.releaseDate = document.getString("releaseDate") ?: ""
            album.artistId = document.getString("artistId") ?: ""
            album.portraitURL = document.getString("portrait") ?: ""
            album.portrait = getPortrait(album)

            val songRefs = document.get("songs") as List<DocumentReference>
            album.songs = ArrayList(fetchSongs(songRefs))
            return album
        }

        private suspend fun fetchSongs(songRefs: List<DocumentReference>): List<Song> {
            return songRefs.map { ref ->
                ref.get().await().toObject<Song>()!!
            }
        }

        suspend fun getPortrait(album: Album): Bitmap {
            val fireBaseStorage = Firebase.storage
            val portraitReference = fireBaseStorage.getReferenceFromUrl(album.portraitURL)

            val bytePortrait = portraitReference.getBytes(1024 * 1024).addOnSuccessListener {
                Log.d("CHRIS_DEBUG", "Portada descargada")
            }.addOnFailureListener {
                Log.d("CHRIS_DEBUG", "Error al descargar la portada")
            }.await()

            return ByteArrayToBitmap(bytePortrait)
        }

        private fun ByteArrayToBitmap(imageData: ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
        }
    }
}