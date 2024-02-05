package pgl.practicafinalpgl.model.login

class Artist: UserType{
    constructor(id: String): super(id, Permissions.ARTIST)
    constructor(
        nickname: String,
        profilePhoto: String,
        ownedPlaylists: ArrayList<String>,
        following: Int,
        followers: Int
    ): super(nickname, profilePhoto, ownedPlaylists, following, followers, Permissions.ARTIST)
}
