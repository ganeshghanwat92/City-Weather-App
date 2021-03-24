package com.example.cityweatherapp.repository.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.cityweatherapp.repository.local.room.entity.CityWeather


@Dao
interface Dao {

    @Query("SELECT * FROM cityweather")
    suspend fun getAll(): List<CityWeather>

    @Query("SELECT * FROM cityweather WHERE `query` = :query")
    suspend fun getCityWeather( query : String): CityWeather

    @Insert
    suspend fun insert(cityWeather: CityWeather)

    @Delete
    suspend fun delete(cityWeather: CityWeather)

    @Query("DELETE FROM cityweather WHERE `query` = :query")
    suspend fun delete(query : String)

    // delete all records whose timestmap is older than 1 days
    @Query("DELETE FROM cityweather WHERE created_at  <= :timeInMillis")
    suspend fun deleteAllRecords(timeInMillis : Long)


}