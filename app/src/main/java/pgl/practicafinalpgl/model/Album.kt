package pgl.practicafinalpgl.model

import com.google.firebase.firestore.DocumentReference
import java.util.ArrayList

class Album(){
    var id: String? = ""
    var name: String = ""
    var songs: ArrayList<DocumentReference> = ArrayList()
    var releaseDate: String = ""
    var artistId: String = ""
    var portait: String = ""
    constructor(id: String = "") : this() {
        this.id = id
    }
}
