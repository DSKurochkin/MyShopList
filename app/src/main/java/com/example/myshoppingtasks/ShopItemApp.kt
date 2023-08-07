package com.example.myshoppingtasks

import android.app.Application
import com.example.myshoppingtasks.di.DaggerAppComponent

class ShopItemApp:Application() {
    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}