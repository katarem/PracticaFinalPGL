package pgl.practicafinalpgl.model

import java.util.ArrayList

class Playlist {
    var id: String?
    var name: String?
    var songs: ArrayList<Song>?
    var subscribedUsersIds: ArrayList<String>?
    var authorId: String?
    var creationDate: String?
    var portait: String?

    constructor(id: String?) {
        this.id = id
        this.name = null
        this.songs = null
        this.subscribedUsersIds = null
        this.authorId = null
        this.creationDate = null
        this.portait = null
    }

    constructor(
        name: String?,
        songs: ArrayList<Song>?,
        subscribedUsersIds: ArrayList<String>?,
        authorId: String?,
        creationDate: String?,
        portait: String?
    ) {
        this.id = null
        this.name = name
        this.songs = songs
        this.subscribedUsersIds = subscribedUsersIds
        this.authorId = authorId
        this.creationDate = creationDate
        this.portait = portait
    }

}