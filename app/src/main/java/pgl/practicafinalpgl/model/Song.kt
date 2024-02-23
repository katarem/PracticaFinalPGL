package pgl.practicafinalpgl.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class Song(){
    var id: String? = ""
    var name: String = ""
    var artistId: String = ""
    var albumId: String = ""
    var portraitURL: String = ""
    var portrait: Bitmap? = null
    var songURL: String = ""


    constructor(id: String): this(){
        this.id = id
    }

    suspend fun loadPortrait(){
        portrait = getPortrait(portraitURL)
    }

    companion object {
        fun toObject(document: DocumentSnapshot): Song {
            val song = Song()
            song.albumId = document.getString("albumId") ?: ""
            song.artistId = document.getString("artistId") ?: ""
            song.id = document.getString("id") ?: ""
            song.name = document.getString("name") ?: ""
            song.portraitURL = document.getString("portraitURL") ?: ""
            song.songURL = document.getString("songURL") ?: ""

            return song
        }
        suspend fun getPortrait(portraitURL: String): Bitmap {
            val fireBaseStorage = Firebase.storage
            val portraitReference = fireBaseStorage.getReferenceFromUrl(portraitURL)

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

    override fun toString(): String {
        return "Song(id=$id, name='$name', artistId='$artistId', albumId='$albumId')"
    }

}
