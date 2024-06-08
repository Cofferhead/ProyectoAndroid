package com.example.examen2pruebaotravez.Repository

import com.example.examen2pruebaotravez.Data.DataClass.Show
import com.example.examen2pruebaotravez.Data.Database.FavoriteShow
import com.example.examen2pruebaotravez.Data.Database.FavoriteShowDao
import com.example.examen2pruebaotravez.Data.ShowService
import kotlinx.coroutines.flow.Flow

class ShowRepository(
    private val showService: ShowService,
    private val favoriteShowDao: FavoriteShowDao
) {
    suspend fun getShowDetail(id: Int) = showService.getShowDetail(id)
    suspend fun searchShow(query: String) = showService.searchShow(query).map { it.toShow() }
    suspend fun getShows(): List<Show> = showService.getShows()

    suspend fun addFavoriteShow(show: FavoriteShow) = favoriteShowDao.addFavorite(show)

    suspend fun removeFavoriteShow(show: FavoriteShow) = favoriteShowDao.deleteFavorite(show)

    fun isFavorite(id: Int): Flow<Boolean> = favoriteShowDao.isFavorite(id)

    fun getFavoriteShows(): Flow<List<FavoriteShow>> = favoriteShowDao.getAllFavorites()


}