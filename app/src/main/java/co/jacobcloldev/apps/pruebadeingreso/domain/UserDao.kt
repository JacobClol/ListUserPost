package co.jacobcloldev.apps.pruebadeingreso.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    suspend fun getUsersDB() : List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserUser(userEntity: List<UserEntity>)
}