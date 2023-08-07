package com.example.myshoppingtasks.di

import android.app.Application
import com.example.myshoppingtasks.presentation.MainActivity
import com.example.myshoppingtasks.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ShopItemFragment)


    @Component.Factory
    interface AppComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}