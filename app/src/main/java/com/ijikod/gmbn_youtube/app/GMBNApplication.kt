package com.ijikod.gmbn_youtube.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/**
 * Base application class for app initialisation and DI
 * **/
@HiltAndroidApp
class GMBNApplication : Application(){


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }



    companion object{
        lateinit var appContext : Context
    }
}