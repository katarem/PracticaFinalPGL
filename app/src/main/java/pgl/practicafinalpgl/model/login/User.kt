package pgl.practicafinalpgl.model.login

class User: UserType{
    constructor(id: String): super(id, Permissions.USER)

    constructor(
        nickname: String,
        profilePhoto: String,
        ownedPlaylists: ArrayList<String>,
        following: Int,
        followers: Int
    ): super(nickname, profilePhoto, ownedPlaylists, following, followers, Permissions.USER)
}
