package pgl.practicafinalpgl.model.login

import java.util.ArrayList




class Admin : UserType {
    override var permissions: Permissions = Permissions.ADMIN

    constructor(): super()
    constructor(id: String): super(id,Permissions.ADMIN)
    constructor(
        nickname: String,
        profilePhoto: String,
        ownedPlaylists: ArrayList<String>,
        following: Int,
        followers: Int
    ): super(nickname, profilePhoto, ownedPlaylists, following, followers, Permissions.ARTIST)


}