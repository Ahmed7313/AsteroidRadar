package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.api.domain.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import java.util.ArrayList

class AsteroidsRepository(private val database: AsteroidDatabase) {

    val asteroids : LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAsteroids()){
        it.asDomainModel()
    }

    suspend fun refreshAsteroids(startDate: String = getToday(), endDate: String = getSeventhDay()){
        withContext(Dispatchers.IO){
            var asteroidList: ArrayList<Asteroid>
            val asteroidsResponse : ResponseBody  = Network.service.getAsteroids(startDate, endDate, Constants.API_KEY).await()

            asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidsResponse.toString()))
            database.asteroidDao.insertAllAsteroids(*asteroidList.asDomainModel())
        }
    }
}