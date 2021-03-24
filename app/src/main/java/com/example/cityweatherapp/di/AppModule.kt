package com.example.cityweatherapp.di

import android.app.Application
import com.example.cityweatherapp.Constants
import com.example.cityweatherapp.repository.Repository
import com.example.cityweatherapp.repository.local.LocalDataSource
import com.example.cityweatherapp.repository.local.room.Dao
import com.example.cityweatherapp.repository.remote.ApiService
import com.example.cityweatherapp.repository.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient{
        return OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(dao: Dao): LocalDataSource {
        return LocalDataSource(dao)
    }

    @Singleton
    @Provides
    fun provideRepository(application: Application,remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource): Repository {
        return Repository(application,remoteDataSource, localDataSource)
    }


}