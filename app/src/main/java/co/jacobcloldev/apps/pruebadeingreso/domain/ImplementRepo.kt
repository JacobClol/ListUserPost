package co.jacobcloldev.apps.pruebadeingreso.domain

import co.jacobcloldev.apps.pruebadeingreso.core.Resource
import co.jacobcloldev.apps.pruebadeingreso.data.Services
import co.jacobcloldev.apps.pruebadeingreso.data.model.PostEntity
import co.jacobcloldev.apps.pruebadeingreso.data.model.PostModel
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserEntity
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserModel

class ImplementRepo(private val dataSource: Services) : Repo{
    override suspend fun getUsers(): Resource<List<UserModel>> {
        return dataSource.getUsers()
    }

    override suspend fun getPostByUser(userId: Long): Resource<List<PostModel>> {
        return dataSource.getPostByUser(userId)
    }

    override suspend fun getUserDB(): Resource<List<UserEntity>> {
        return dataSource.getUserDB()
    }

    override suspend fun insertUserDB(userEntity: List<UserEntity>) {
        dataSource.insertUserDB(userEntity)
    }

    override suspend fun getPostByUserDB(userId: Long): Resource<List<PostEntity>> {
        return dataSource.getPostByUserDB(userId)
    }

    override suspend fun insertPost(postEntity: List<PostEntity>) {
       dataSource.insertPost(postEntity)
    }
}