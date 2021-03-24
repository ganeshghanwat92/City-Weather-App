package com.example.cityweatherapp

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.cityweatherapp.di.AppComponent
import com.example.cityweatherapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApp : Application(), HasAndroidInjector {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject lateinit var workerFactory: WorkerFactory

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().application(this).build()

        appComponent.inject(this)

        // register custom factory to WorkerManager
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(workerFactory).build())

    }

    override fun androidInjector(): AndroidInjector<Any> {
       return dispatchingAndroidInjector
    }


}