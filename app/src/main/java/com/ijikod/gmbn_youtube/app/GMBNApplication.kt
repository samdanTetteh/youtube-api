package com.ijikod.gmbn_youtube.app

import android.app.Application
import android.content.Context

/**
 * Base application class for app initialisation and DI
 * **/
class GMBNApplication : Application(){


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }



    companion object{
        lateinit var appContext : Context
    }
}