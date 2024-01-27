package pgl.practicafinalpgl.model.login

import pgl.practicafinalpgl.model.UserType

data class User(
    var id: String,
    var nickname: String?,
    var profilePhoto: String?,
    var ownedPlaylists: List<String>?,
    var following: Int?,
    var followers: Int?
): UserType(id, nickname, profilePhoto, ownedPlaylists, following, followers, permissions = Permissions.USER) {
    constructor(id: String): this(id,null,null,null,null,null)
}
