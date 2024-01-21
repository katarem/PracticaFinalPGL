package pgl.practicafinalpgl.model

data class Playlist(val id: String, val name: String?, val songs: List<Song>?,val suscribedUsersIds: List<String>?, val authorId: String?, val creationDate: String?, val portait: String?): Entity(){
    constructor(id: String): this(id,null,null,null,null,null,null)
}
