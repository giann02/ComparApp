package com.example.comparapp

import android.app.Application

class ComparAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContainer.init(this)
    }
}
