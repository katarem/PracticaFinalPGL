package pgl.practicafinalpgl.db

interface Repository<T> {
    var repository: ArrayList<T>
    fun insert(t: T)
    fun getById(t: T): T
    fun getAll(): List<T>
    fun remove(t: T): Boolean
    fun update(t: T): Boolean

}