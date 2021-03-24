package com.example.cityweatherapp.repository.local

import com.example.cityweatherapp.repository.local.room.Dao
import com.example.cityweatherapp.repository.local.room.entity.CityWeather
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: Dao) {


    suspend fun getWeatherDetails(query : String) : CityWeather?{

       return dao.getCityWeather(query)

    }

    suspend fun insertWeatherRecord(cityWeather: CityWeather){

        dao.insert(cityWeather = cityWeather)

    }

    suspend fun deleteWeatherRecord(query: String){

        dao.delete(query)

    }

    suspend fun deleteOldWeatherRecords(currentMillis : Long){

        val millis24Hr = 86400000

        val milliSec = currentMillis - millis24Hr

        dao.deleteAllRecords(milliSec)

    }

}