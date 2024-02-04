package pgl.practicafinalpgl.model

class Playlist(
    id: String,
    name: String?,
    songs: List<Song>?,
    suscribedUsersIds: List<String>?,
    authorId: String?,
    creationDate: String?,
    portait: String?){
    constructor(id: String): this(id,null,null,null,null,null,null)
}
