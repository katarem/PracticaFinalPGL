package pgl.practicafinalpgl.model

class Song{
    var id: String?
    var name: String?
    var artistId: String?
    var albumId: String?
    constructor(
        id: String?
    ){
        this.id = id
        this.name = null
        this.artistId = null
        this.albumId = null
    }

    constructor(
        name: String,
        artistId: String,
        albumId: String
    ){
        this.id = null
        this.name = name
        this.artistId = artistId
        this.albumId = albumId
    }


}
