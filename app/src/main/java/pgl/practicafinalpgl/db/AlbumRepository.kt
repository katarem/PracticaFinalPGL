package pgl.practicafinalpgl.db

import pgl.practicafinalpgl.model.Album

class AlbumRepository: Repository<Album> {

    override var repository = arrayListOf<Album>()
    override fun insert(t: Album) {
        this.repository.add(t)
    }
    override fun getById(t: Album): Album {
        return this.repository.stream().filter{album -> album.id == t.id}.findAny().orElse(null)
    }

    override fun getAll(): List<Album> {
        return this.repository
    }

    override fun update(t: Album): Boolean {
        val old = this.repository.stream().filter{album -> album.id == t.id}.findAny().orElse(null)
        if(old != null){
            this.repository.remove(old)
            this.repository.add(t)
            return true
        }
        return false
    }

    override fun remove(t: Album): Boolean {
        val old = this.repository.stream().filter{album -> album.id == t.id}.findAny().orElse(null)
        if(old != null) {
            this.repository.remove(old)
            this.repository.add(t)
            return true
        }
        return false
    }
}