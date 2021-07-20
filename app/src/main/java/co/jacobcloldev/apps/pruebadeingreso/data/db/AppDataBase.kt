package co.jacobcloldev.apps.pruebadeingreso.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.jacobcloldev.apps.pruebadeingreso.data.model.PostEntity
import co.jacobcloldev.apps.pruebadeingreso.data.model.UserEntity
import co.jacobcloldev.apps.pruebadeingreso.domain.PostDao
import co.jacobcloldev.apps.pruebadeingreso.domain.UserDao

@Database(entities = arrayOf(UserEntity::class, PostEntity::class), version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object{
        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "user_table").build()
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}