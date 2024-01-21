package pgl.practicafinalpgl.db

import pgl.practicafinalpgl.model.Playlist

class PlaylistRepository: Repository<Playlist>{

    override var repository = listOf<Playlist>()
    override fun insert(t: Playlist) {
        TODO("Not yet implemented")
    }

    override fun getById(t: Playlist): Playlist {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Playlist> {
        TODO("Not yet implemented")
    }

    override fun update(t: Playlist): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(t: Playlist): Boolean {
        TODO("Not yet implemented")
    }

}