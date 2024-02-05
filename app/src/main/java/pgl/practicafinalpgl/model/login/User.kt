package pgl.practicafinalpgl.model.login

class User(): UserType(){

    override var permissions: Permissions = Permissions.USER
    constructor(id: String): this() {
        this.id = id
    }
}
