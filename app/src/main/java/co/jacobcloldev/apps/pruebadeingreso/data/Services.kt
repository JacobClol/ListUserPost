package co.jacobcloldev.apps.pruebadeingreso.data

import co.jacobcloldev.apps.pruebadeingreso.core.Resource
import co.jacobcloldev.apps.pruebadeingreso.core.RetrofitClient
import co.jacobcloldev.apps.pruebadeingreso.data.db.AppDataBase
import co.jacobcloldev.apps.pruebadeingreso.data.model.PostEntity
import co.jacobcloldev.apps.pruebadeingreso.data.model.PostModel
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserEntity
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserModel

class Services(private val appDataBase: AppDataBase) {

    suspend fun getUsers(): Resource<List<UserModel>> {
        return Resource.Success(RetrofitClient.webservice.getUsers())
    }

    suspend fun getPostByUser(userId: Long): Resource<List<PostModel>>{
        return Resource.Success(RetrofitClient.webservice.getPostById(userId))
    }

    suspend fun getUserDB(): Resource<List<UserEntity>> {
        return Resource.Success(appDataBase.userDao().getUsersDB())
    }

    suspend fun insertUserDB(userEntity: List<UserEntity>){
        appDataBase.userDao().inserUser(userEntity)
    }

    suspend fun getPostByUserDB(userId: Long): Resource<List<PostEntity>>{
        return Resource.Success(appDataBase.postDao().getUsersDB(userId))
    }

    suspend fun insertPost(postEntity: List<PostEntity>){
        appDataBase.postDao().inserPost(postEntity)
    }
}