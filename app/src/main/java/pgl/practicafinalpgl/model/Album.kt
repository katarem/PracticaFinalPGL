package pgl.practicafinalpgl.model

data class Album(val id: String?, val name: String?, val songs: List<Song>?, val releaseDate: String?, val artistId: String?, val portait: String?): Entity(){
    constructor(id: String) : this(id,null,null,null,null,null)
}