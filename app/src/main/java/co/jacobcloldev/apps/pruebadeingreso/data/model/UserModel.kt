package co.jacobcloldev.apps.pruebadeingreso.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: String
)


data class PostModel(
    @SerializedName("userId")
    var userId: Long,

    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("body")
    val body: String

)