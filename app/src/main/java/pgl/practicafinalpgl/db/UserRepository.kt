package pgl.practicafinalpgl.db

import pgl.practicafinalpgl.model.Playlist
import pgl.practicafinalpgl.model.login.User

class UserRepository: Repository<User> {
    override var repository = arrayListOf<User>()
    override fun insert(t: User) {
        this.repository.add(t)
    }
    override fun getById(t: User): User {
        return this.repository.stream().filter{playlist -> playlist.id == t.id}.findAny().orElse(null)
    }

    override fun getAll(): List<User> {
        return this.repository
    }

    override fun update(t: User): Boolean {
        val old = this.repository.stream().filter{user -> user.id == t.id}.findAny().orElse(null)
        if(old != null){
            this.repository.remove(old)
            this.repository.add(t)
            return true
        }
        return false
    }

    override fun remove(t: User): Boolean {
        val old = this.repository.stream().filter{user -> user.id == t.id}.findAny().orElse(null)
        if(old != null) {
            this.repository.remove(old)
            return true
        }
        return false
    }
}