package com.example.examen2pruebaotravez.Data.Database

import androidx.room.Entity
import com.example.examen2pruebaotravez.Data.DataClass.Image
import com.example.examen2pruebaotravez.Data.DataClass.Rating
import com.example.examen2pruebaotravez.Data.DataClass.Show
import com.example.examen2pruebaotravez.Data.FAVORITE_SHOWS_TABLE

@Entity(tableName = FAVORITE_SHOWS_TABLE, primaryKeys = ["id"])
data class FavoriteShow(
    val id: Int,
    val name: String,
    val image: String,
    val rate: String,
    val genres: String,
)

fun FavoriteShow.toShow() = Show(
    id = id,
    name = name,
    image = Image(medium = image, original = image),
    rating = Rating(average = rate.toDouble()),
    genres = genres.split(",")
)