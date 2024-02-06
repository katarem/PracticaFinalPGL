package pgl.practicafinalpgl.model

import com.google.firebase.firestore.DocumentReference
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
}