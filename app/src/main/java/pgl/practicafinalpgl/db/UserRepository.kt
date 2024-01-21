package pgl.practicafinalpgl.db

import pgl.practicafinalpgl.model.login.User

class UserRepository: Repository<User> {
    override var repository = listOf<User>()
    override fun insert(t: User) {
        TODO("Not yet implemented")
    }
    override fun getById(t: User): User {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<User> {
        TODO("Not yet implemented")
    }

    override fun update(t: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(t: User): Boolean {
        TODO("Not yet implemented")
    }
}