package com.example.cityweatherapp.di

import android.app.Application
import com.example.cityweatherapp.MyApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class,  MainActivityModule::class, FragmentsModule::class,AppModule::class, ViewModelModule::class,DatabaseModule::class, WorkerBindingModule::class])
interface AppComponent{

    fun inject(app : MyApp)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application :Application ): Builder
    }


}