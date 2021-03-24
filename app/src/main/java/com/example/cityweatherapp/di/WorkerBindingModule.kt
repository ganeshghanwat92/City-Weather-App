package com.example.cityweatherapp.di

import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkerFactory
import com.example.cityweatherapp.ViewModelFactory
import com.example.cityweatherapp.wm.DeleteOldWeatherRecordsWorker
import com.example.cityweatherapp.wm.MyWorkerFactory
import com.example.cityweatherapp.wm.SampleWorkerFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WorkerBindingModule {

    @Binds
    abstract fun bindSampleWorkerFactory(factory: SampleWorkerFactory): WorkerFactory


    @Binds
    @IntoMap
    @WorkerKey(DeleteOldWeatherRecordsWorker::class)
   abstract fun bindDeleteOldWeatherRecordsWorker(factory: DeleteOldWeatherRecordsWorker.Factory): MyWorkerFactory
}