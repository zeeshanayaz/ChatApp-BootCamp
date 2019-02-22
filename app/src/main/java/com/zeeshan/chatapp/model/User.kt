package com.zeeshan.chatapp.model

import com.google.firebase.database.Exclude

data class User(var userID : String = "", var userName : String? = null, var userEmail : String = "", var password: String = "", var userBio : String? = null) {

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
//    @Exclude
//    fun toMap(): Map<String, Any?>{
//        return mapOf(
//            "userID" to userID,
//            "userName" to userName,
//            "userEmail" to userEmail,
//            "password" to password,
//            "userBio" to userBio
//        )
//    }
}