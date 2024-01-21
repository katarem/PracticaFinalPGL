package pgl.practicafinalpgl.db

import pgl.practicafinalpgl.model.Album

class AlbumRepository: Repository<Album> {

    override var repository = listOf<Album>()
    override fun insert(t: Album) {
        TODO("Not yet implemented")
    }
    override fun getById(t: Album): Album {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Album> {
        TODO("Not yet implemented")
    }

    override fun update(t: Album): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(t: Album): Boolean {
        TODO("Not yet implemented")
    }
}