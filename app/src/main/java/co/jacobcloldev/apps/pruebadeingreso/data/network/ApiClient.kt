package co.jacobcloldev.apps.pruebadeingreso.data.network

import co.jacobcloldev.apps.pruebadeingreso.core.Endpoints
import co.jacobcloldev.apps.pruebadeingreso.data.model.PostModel
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET(Endpoints.GET_POST_USER)
    suspend fun getPostById(@Query("userId") idUser: Long): List<PostModel>

    @GET(Endpoints.GET_USERS)
    suspend fun getUsers(): List<UserModel>
}