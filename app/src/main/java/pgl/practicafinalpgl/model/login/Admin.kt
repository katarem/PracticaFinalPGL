package pgl.practicafinalpgl.model.login

import pgl.practicafinalpgl.model.UserType

class Admin(
    id: String,
    nickname: String?,
    profilePhoto: String?,
    ownedPlaylists: List<String>?,
    following: Int?,
    followers: Int?
) : UserType(id, nickname, profilePhoto, ownedPlaylists, following, followers, Permissions.ADMIN) {
    constructor(id: String): this(id, null,null,null,null,null)
}