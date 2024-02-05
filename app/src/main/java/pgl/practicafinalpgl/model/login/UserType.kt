package pgl.practicafinalpgl.model.login

import pgl.practicafinalpgl.model.login.Permissions

open class UserType {
    var id: String?
    var nickname: String?
    var profilePhoto: String?
    var ownedPlaylists: ArrayList<String>?
    var following: Int?
    var followers: Int?
    var permissions: Permissions

    constructor(id: String, permissions: Permissions) {
        this.id = id
        nickname = null
        profilePhoto = null
        ownedPlaylists = null
        ownedPlaylists = null
        following = null
        followers = null
        this.permissions = permissions
    }

    constructor(
        nickname: String,
        profilePhoto: String,
        ownedPlaylists: ArrayList<String>,
        following: Int,
        followers: Int,
        permissions: Permissions
    ) {
        this.id = null
        this.nickname = nickname
        this.profilePhoto = profilePhoto
        this.ownedPlaylists = ownedPlaylists
        this.following = following
        this.followers = followers
        this.permissions = permissions
    }
}