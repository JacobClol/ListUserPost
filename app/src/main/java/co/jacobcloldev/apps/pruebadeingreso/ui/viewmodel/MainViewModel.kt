package co.jacobcloldev.apps.pruebadeingreso.ui.viewmodel

import androidx.lifecycle.*
import co.jacobcloldev.apps.pruebadeingreso.core.Resource
import co.jacobcloldev.apps.pruebadeingreso.data.model.PostEntity
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserEntity
import co.jacobcloldev.apps.pruebadeingreso.domain.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repo): ViewModel() {

    private val idUser = MutableLiveData<Long>()

    fun setUserId(userId: Long){
        idUser.value = userId
    }

    val fetchUsers = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getUsers())
        } catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    val fetchPostByUser = idUser.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repo.getPostByUser(it))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    val getUserDB = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getUserDB())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun getPostDB(userId: Long) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getPostByUserDB(userId))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun insertUserDB(userEntity: List<UserEntity>){
        viewModelScope.launch {
            repo.insertUserDB(userEntity)
        }
    }

    fun insertPostDB(postEntity: List<PostEntity>){
        viewModelScope.launch {
            repo.insertPost(postEntity)
        }
    }
}