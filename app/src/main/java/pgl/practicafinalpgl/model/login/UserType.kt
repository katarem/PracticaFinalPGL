package pgl.practicafinalpgl.model.login

import pgl.practicafinalpgl.model.login.Permissions

open class UserType(){
    var id: String? = ""
    var nickname: String? = ""
    var profilePhoto: String? = ""
    //var ownedPlaylists: ArrayList<String>?
    var following: Int? = 0
    var followers: Int? = 0
    open var permissions: Permissions = Permissions.USER

    constructor(id: String, permissions: Permissions): this(){
        this.id = id
        this.permissions = permissions
    }
}