package pgl.practicafinalpgl.model

data class Song(val id: String, val name: String?, val artistId: String?, val albumId: String?): Entity(){
    constructor(id: String): this(id,null,null,null)
}