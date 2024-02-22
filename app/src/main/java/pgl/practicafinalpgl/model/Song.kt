package pgl.practicafinalpgl.model

import com.google.firebase.firestore.DocumentSnapshot

class Song(){
    var id: String? = ""
    var name: String = ""
    var artistId: String = ""
    var albumId: String = ""
    var file: String = ""


    constructor(id: String): this(){
        this.id = id
    }

    companion object {
        fun toObject(document: DocumentSnapshot): Song {
            val song = Song()
            song.id = document.getString("id") ?: ""
            song.name = document.getString("name") ?: ""
            song.artistId = document.getString("artistId") ?: ""
            song.albumId = document.getString("albumId") ?: ""
            return song
        }
    }

    override fun toString(): String {
        return "Song(id=$id, name='$name', artistId='$artistId', albumId='$albumId')"
    }

}
