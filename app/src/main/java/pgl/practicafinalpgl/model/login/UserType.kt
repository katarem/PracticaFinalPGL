package pgl.practicafinalpgl.model.login

import com.google.firebase.firestore.QueryDocumentSnapshot
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

    companion object {
        fun toObject(document: QueryDocumentSnapshot): UserType {
            val userType = UserType()

            userType.id = document.id
            userType.nickname = document.getString("nickname")
            userType.profilePhoto = document.getString("profilePhoto")
            userType.following = document.getLong("following")?.toInt() ?: 0
            userType.followers = document.getLong("followers")?.toInt() ?: 0
            userType.permissions = Permissions.valueOf(document.getString("permissions") ?: Permissions.USER.name)

            // Puedes continuar asignando valores para otros campos según tu esquema de Firestore

            return userType
        }
    }
}