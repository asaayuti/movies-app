package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.core.di.CoreComponent
import com.example.moviesapp.core.di.DaggerCoreComponent
import com.example.moviesapp.di.AppComponent
import com.example.moviesapp.di.DaggerAppComponent

open class MyApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}