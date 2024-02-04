package pgl.practicafinalpgl.model

class Song(
    id: String?,
    name: String?,
    artistId: String?,
    albumId: String?
){

    constructor() : this(null, null, null, null)

    constructor(id: String) : this(id, null, null, null)
}
