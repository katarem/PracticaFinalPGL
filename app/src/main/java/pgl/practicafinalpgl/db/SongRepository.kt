package pgl.practicafinalpgl.db

import pgl.practicafinalpgl.model.Song

class SongRepository: Repository<Song> {

    override var repository: List<Song> = listOf()
    override fun getById(t: Song): Song {
        TODO("Not yet implemented")
    }
    override fun insert(t: Song) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Song> {
        TODO("Not yet implemented")
    }

    override fun update(t: Song): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(t: Song): Boolean {
        TODO("Not yet implemented")
    }
}