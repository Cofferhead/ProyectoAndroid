package com.example.examen2pruebaotravez.Data.DataClass

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import com.example.examen2pruebaotravez.Data.Database.FavoriteShow
import com.squareup.moshi.Json
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class Show(
    val id: Int = 0,
    val name: String = "",
    val genres: List<String> = emptyList(),
    val rating: Rating = Rating(),
    val image: Image? = Image(),
    @Json(name = "summary") val description: String? = "",
    val network : Network? = Network(),
    val premiered: String = "",
    val language: String = "",
    var isFavorite: Boolean? = false
) : Serializable
{
    fun toFormattedDescription(): String {
        return HtmlCompat.fromHtml(this.description ?: " ", HtmlCompat.FROM_HTML_MODE_COMPACT)
            .toString()
    }

    fun toGenresString(): String {
        return this.genres.ifEmpty { listOf("No genre") }.joinToString(separator = ", ")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toPremieredString(): String {
        return premiered.ifEmpty { "2009-05-19" }.let {
            val date = LocalDate.parse(it)
            date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
        }
    }
    fun toFavoriteShow(): FavoriteShow {
        return FavoriteShow(
            id = this.id,
            name = this.name,
            genres = this.toGenresString(),
            image = this.image?.medium ?: "",
            rate = this.rating.average.toString(),
        )
    }

}


data class ShowSearch(
    val score: Double? = 0.0,
    val show: Show = Show()
):Serializable {
    fun toShow() = show
}

data class Rating(
    val average: Double? = 0.0
):Serializable

data class Image(
    val medium: String = "",
    val original: String = ""

):Serializable

data class Network(
    val id: Int = 0,
    val name: String? = "",
    val country: Country? = Country()
) :Serializable

data class Country(
    val name: String = "",
    val code: String = "",
    val timezone: String = ""
) :Serializable {
    override fun toString(): String {
        return "$name, $code"
    }
}
/*
data class Show(
    val _links: Links,
    val averageRuntime: Int,
    val dvdCountry: Any,
    val ended: String,
    val externals: Externals,
    val genres: List<String>,
    val id: Int,
    val image: Image,
    val language: String,
    val name: String,
    val network: Network,
    val officialSite: String,
    val premiered: String,
    val rating: Rating,
    val runtime: Int,
    val schedule: Schedule,
    val status: String,
    val summary: String,
    val type: String,
    val updated: Int,
    val url: String,
    val webChannel: Any,
    val weight: Int
) : Serializable*/