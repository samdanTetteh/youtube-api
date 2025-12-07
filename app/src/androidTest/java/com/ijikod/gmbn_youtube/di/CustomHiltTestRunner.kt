package com.ijikod.gmbn_youtube.di

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class CustomHiltTestRunner: AndroidJUnitRunner() {
    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        // Use HiltTestApplication for tests
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}