package pgl.practicafinalpgl.model

class Song(
    val id: String?,
    val name: String?,
    val artistId: String?,
    val albumId: String?
) : Entity() {

    constructor() : this(null, null, null, null)

    constructor(id: String) : this(id, null, null, null)
}
