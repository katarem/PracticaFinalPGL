package pgl.practicafinalpgl.model.login

class Admin() : UserType() {
    override var permissions: Permissions = Permissions.ADMIN
    constructor(id: String): this(){
        this.id = id
    }
}