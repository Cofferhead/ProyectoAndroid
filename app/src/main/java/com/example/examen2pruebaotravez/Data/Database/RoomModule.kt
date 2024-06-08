package com.example.examen2pruebaotravez.Data.Database

import android.content.Context
import androidx.room.Room
import com.example.examen2pruebaotravez.Data.DATABASE_NAME
import com.example.examen2pruebaotravez.Repository.ShowRepository
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import javax.inject.Singleton

@Module
object RoomModule {
    @Provides
    @Singleton
    fun providesFavoriteDatabase(
        context: Context
    ) = Room.databaseBuilder(
        context,
        FavoriteShowDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun providesFavoriteShowDao(
        db: FavoriteShowDatabase
    ) = db.favoriteShowDao()
}