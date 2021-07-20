package co.jacobcloldev.apps.pruebadeingreso.domain

import co.jacobcloldev.apps.pruebadeingreso.core.Resource
import co.jacobcloldev.apps.pruebadeingreso.data.model.PostEntity
import co.jacobcloldev.apps.pruebadeingreso.data.model.PostModel
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserEntity
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserModel

interface Repo {
    suspend fun getUsers(): Resource<List<UserModel>>

    suspend fun getPostByUser(userId: Long): Resource<List<PostModel>>

    suspend fun getUserDB(): Resource<List<UserEntity>>

    suspend fun insertUserDB(userEntity: List<UserEntity>)

    suspend fun getPostByUserDB(userId: Long): Resource<List<PostEntity>>

    suspend fun insertPost(postEntity: List<PostEntity>)
}