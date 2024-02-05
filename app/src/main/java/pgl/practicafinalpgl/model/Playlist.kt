package pgl.practicafinalpgl.model

import java.util.ArrayList

class Playlist(){
    var id: String? = ""
    var name: String = ""
    //var songs: ArrayList<Song>? =
    //var subscribedUsersIds: ArrayList<String>?
    var authorId: String = ""
    var creationDate: String = ""
    var portait: String = ""

    constructor(id: String?): this() {
        this.id = id
    }
}