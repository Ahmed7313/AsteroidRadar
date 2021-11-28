package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM databaseAsteroid ORDER BY closeApproachDate ASC")
    fun getAsteroids() : LiveData<List<DatabaseAsteroid>>

    @Insert
    fun insertAllAsteroids(vararg asteroid: DatabaseAsteroid)
}

@Database(entities = [AsteroidDatabase::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase(){
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java){
    if (!::INSTANCE.isInitialized){
        INSTANCE = Room.databaseBuilder(context.applicationContext,
            AsteroidDatabase::class.java,
            "Asteroids").build()
    }
    }
    return INSTANCE
}
