package com.example.examen2pruebaotravez.Data

import com.example.examen2pruebaotravez.Data.Database.FavoriteShowDao
import com.example.examen2pruebaotravez.Repository.ShowRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val showRepository: ShowRepository
}


class DefaultAppContainer : AppContainer {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()



    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: ShowService by lazy {
        retrofit.create(ShowService::class.java)
    }
    private val favoriteShowDao:FavoriteShowDao by lazy {
        retrofit.create(FavoriteShowDao::class.java)
    }

    override val showRepository: ShowRepository by lazy {
        ShowRepository(retrofitService, favoriteShowDao)
    }
}