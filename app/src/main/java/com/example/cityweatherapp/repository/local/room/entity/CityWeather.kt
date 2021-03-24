package com.example.cityweatherapp.repository.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cityweather")
data class CityWeather(

    @PrimaryKey
    @ColumnInfo(name = "query")
    val query: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    @ColumnInfo(name = "lat")
    val lat : Double,

    @ColumnInfo(name = "lon")
    val lon : Double,

    @ColumnInfo(name = "weather_id")
    val weatherId : Int,

    @ColumnInfo(name = "weather_main")
    val weatherMain : String,

    @ColumnInfo(name = "weather_desc")
    val weatherDesc : String,

    @ColumnInfo(name = "weather_icon")
    val weatherIcon : String,

    @ColumnInfo(name = "city_name")
    val cityName : String,

    @ColumnInfo(name = "main_temp")
    val mainTemp : Double,

    @ColumnInfo(name = "main_feels_like")
    val mainFeelsLike : Double,

    @ColumnInfo(name = "main_pressure")
    val mainPressure  : Int,

     @ColumnInfo(name = "main_humidity ")
    val mainHumidity   : Int,

    @ColumnInfo(name = "main_temp_min")
    val mainTempMin : Double,

    @ColumnInfo(name = "main_temp_max")
    val mainTempMax : Double

)