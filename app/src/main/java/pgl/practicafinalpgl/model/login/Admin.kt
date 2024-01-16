package pgl.practicafinalpgl.model.login

import pgl.practicafinalpgl.model.UserType

class Admin(
    id: Long,
    nickname: String,
    profilePhoto: String,
    ownedPlaylists: List<Long>,
    following: Int,
    followers: Int
) : UserType(id, nickname, profilePhoto, ownedPlaylists, following, followers, Permissions.ADMIN) {
}