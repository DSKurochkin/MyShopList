package com.example.myshoppingtasks.di

import android.app.Application
import com.example.myshoppingtasks.data.AppDB
import com.example.myshoppingtasks.data.RoomRepoImpl
import com.example.myshoppingtasks.data.ShopItemDao
import com.example.myshoppingtasks.domain.Repo
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @AppScope
    @Binds
    fun bindRepo(impl: RoomRepoImpl): Repo

    companion object {
        @AppScope
        @Provides
        fun provideDao(application: Application): ShopItemDao {
            return AppDB.getInstance(application).shopItemDao()
        }
    }

}