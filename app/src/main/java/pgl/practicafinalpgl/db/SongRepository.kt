package pgl.practicafinalpgl.db

import pgl.practicafinalpgl.model.Playlist
import pgl.practicafinalpgl.model.Song

class SongRepository: Repository<Song> {

    override var repository = arrayListOf<Song>()
    override fun insert(t: Song) {
        this.repository.add(t)
    }
    override fun getById(t: Song): Song {
        return this.repository.stream().filter{song -> song.id == t.id}.findAny().orElse(null)
    }

    override fun getAll(): List<Song> {
        return this.repository
    }

    override fun update(t: Song): Boolean {
        val old = this.repository.stream().filter{song -> song.id == t.id}.findAny().orElse(null)
        if(old != null){
            this.repository.remove(old)
            this.repository.add(t)
            return true
        }
        return false
    }

    override fun remove(t: Song): Boolean {
        val old = this.repository.stream().filter{song -> song.id == t.id}.findAny().orElse(null)
        if(old != null) {
            this.repository.remove(old)
            return true
        }
        return false
    }
}