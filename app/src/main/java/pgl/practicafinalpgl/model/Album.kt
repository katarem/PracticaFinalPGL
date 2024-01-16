package pgl.practicafinalpgl.model

data class Album(val id: Long, val name: String, val songs: List<Song>, val releaseDate: String, val artistId: Long, val portait: String)