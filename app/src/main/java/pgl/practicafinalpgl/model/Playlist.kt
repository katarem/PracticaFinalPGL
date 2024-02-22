package pgl.practicafinalpgl.model

import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.ArrayList

class Playlist(){
    var id: String? = ""
    var name: String = ""
    var songs: ArrayList<Song> = ArrayList()
    //var subscribedUsersIds: ArrayList<String>?
    var authorId: String = ""
    var creationDate: String = ""
    var portait: String = ""

    constructor(id: String?): this() {
        this.id = id
    }

    companion object {
        fun toObject(document: QueryDocumentSnapshot): Playlist {
            val playlist = Playlist()

            playlist.id = document.id
            playlist.name = document.getString("name") ?: ""
            playlist.authorId = document.getString("authorId") ?: ""
            playlist.creationDate = document.getString("creationDate") ?: ""
            playlist.portait = document.getString("portait") ?: ""

            val songsList = document.get("songs") as ArrayList<Song>
            playlist.songs = songsList

            return playlist
        }
    }
}