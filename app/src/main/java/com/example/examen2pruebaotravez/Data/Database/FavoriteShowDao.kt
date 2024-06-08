package com.example.examen2pruebaotravez.Data.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteShowDao {
    @Query("SELECT * FROM favorite_shows ORDER BY name ASC")
    fun getAllFavorites(): Flow<List<FavoriteShow>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favoriteShow: FavoriteShow)
    @Delete
    suspend fun deleteFavorite(favoriteShow: FavoriteShow)

    @Query("SELECT EXISTS(SELECT * FROM favorite_shows WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>
}