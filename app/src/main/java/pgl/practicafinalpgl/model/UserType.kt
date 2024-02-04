package pgl.practicafinalpgl.model

import pgl.practicafinalpgl.model.login.Permissions

open class UserType(
    id: String,
    nickname: String?,
    profilePhoto: String?,
    ownedPlaylists: List<String>?,
    following: Int?,
    followers: Int?,
    permissions: Permissions?
){
    constructor(id: String): this(id,null,null,null,null,null,null)
}