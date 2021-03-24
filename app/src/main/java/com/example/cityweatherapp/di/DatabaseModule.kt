package com.example.cityweatherapp.di

import android.app.Application
import androidx.room.Room
import com.example.cityweatherapp.repository.local.room.Dao
import com.example.cityweatherapp.repository.local.room.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(application: Application) : Database {

        return Room.databaseBuilder(application,Database::class.java,"weather_db").build()

    }

    @Singleton
    @Provides
    fun provideCityWeatherDao(database: Database) : Dao{

       return database.dao()

    }

}