package com.example.cityweatherapp.repository.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cityweatherapp.repository.local.room.entity.CityWeather

@Database(entities = [CityWeather::class], version = 1)
abstract class Database : RoomDatabase(){

    abstract fun dao(): Dao

}