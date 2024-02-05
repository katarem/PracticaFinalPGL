package pgl.practicafinalpgl.model.login

class Artist(): UserType(){

    override var permissions: Permissions = Permissions.ARTIST

    constructor(id: String): this(){
        this.id = id
    }

}
