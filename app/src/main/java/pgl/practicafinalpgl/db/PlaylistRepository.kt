package pgl.practicafinalpgl.db

import pgl.practicafinalpgl.model.Album
import pgl.practicafinalpgl.model.Playlist

class PlaylistRepository: Repository<Playlist>{

    override var repository = arrayListOf<Playlist>()
    override fun insert(t: Playlist) {
        this.repository.add(t)
    }
    override fun getById(t: Playlist): Playlist {
        return this.repository.stream().filter{playlist -> playlist.id == t.id}.findAny().orElse(null)
    }

    override fun getAll(): List<Playlist> {
        return this.repository
    }

    override fun update(t: Playlist): Boolean {
        val old = this.repository.stream().filter{playlist -> playlist.id == t.id}.findAny().orElse(null)
        if(old != null){
            this.repository.remove(old)
            this.repository.add(t)
            return true
        }
        return false
    }

    override fun remove(t: Playlist): Boolean {
        val old = this.repository.stream().filter{playlist -> playlist.id == t.id}.findAny().orElse(null)
        if(old != null) {
            this.repository.remove(old)
            return true
        }
        return false
    }

}