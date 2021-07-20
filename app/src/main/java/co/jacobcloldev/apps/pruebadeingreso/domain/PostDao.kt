package co.jacobcloldev.apps.pruebadeingreso.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.jacobcloldev.apps.pruebadeingreso.data.model.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity WHERE userId == :userId")
    suspend fun getUsersDB(userId: Long) : List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserPost(postEntity: List<PostEntity>)
}