package com.example.examen2pruebaotravez

import android.app.Application
import com.example.examen2pruebaotravez.Data.AppContainer
import com.example.examen2pruebaotravez.Data.DefaultAppContainer

class ShowApplication : Application() {
    lateinit var container: AppContainer


    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}