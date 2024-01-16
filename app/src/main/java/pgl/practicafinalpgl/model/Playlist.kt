package pgl.practicafinalpgl.model

data class Playlist(val id: Long, val name: String, val songs: List<Song>,val suscribedUsersIds: List<Long>, val authorId: Long, val creationDate: String, val portait: String)
