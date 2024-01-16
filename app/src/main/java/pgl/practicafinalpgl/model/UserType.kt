package pgl.practicafinalpgl.model

import pgl.practicafinalpgl.model.login.Permissions

open class UserType(
    id: Long,
    nickname: String,
    profilePhoto: String,
    ownedPlaylists: List<Long>,
    following: Int,
    followers: Int,
    permissions: Permissions
){

}