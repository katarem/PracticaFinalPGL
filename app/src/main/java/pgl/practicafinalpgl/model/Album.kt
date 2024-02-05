package pgl.practicafinalpgl.model

import java.util.ArrayList

class Album{
    var id: String?
    var name: String?
    var songs: ArrayList<Song>?
    var releaseDate: String?
    var artistId: String?
    var portait: String?
    constructor(
        name: String,
        songs: ArrayList<Song>,
        releaseDate: String,
        artistId: String,
        portait: String
    ){
        this.id = null
        this.name = name
        this.songs = songs
        this.releaseDate = releaseDate
        this.artistId = artistId
        this.portait = portait
    }
    constructor(id: String){
        this.id = id
        this.name = null
        this.songs = null
        this.releaseDate = null
        this.artistId = null
        this.portait = null
    }
}
