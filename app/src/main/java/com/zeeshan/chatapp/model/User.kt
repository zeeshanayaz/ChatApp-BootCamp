package com.zeeshan.chatapp.model

import java.io.Serializable

data class User(
    var userID: String = "",
    var userName: String? = null,
    var userEmail: String = "",
    var password: String = "",
    var userBio: String? = null,
    var profileImageUrl: String? = "",
    var refistrationTokenFCM : MutableList<String>
) : Serializable {

    override fun toString(): String {
        return "${userEmail.toString()}"
    }

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (userID != other.userID) return false

        return true
    }
}