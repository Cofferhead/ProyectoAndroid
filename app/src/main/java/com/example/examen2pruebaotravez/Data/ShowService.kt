package com.example.examen2pruebaotravez.Data

import com.example.examen2pruebaotravez.Data.DataClass.Show
import com.example.examen2pruebaotravez.Data.DataClass.ShowSearch
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ShowService {

    @GET(SEARCH_SHOW_ENDPOINT)
    suspend fun searchShow(
        //@Query("q")
        query: String
    ): List<ShowSearch>

    @GET(SHOW_DETAIL_ENDPOINT)
    suspend fun getShowDetail(
        @Path("id") id: Int
    ): Show

    @GET(SHOWS_ENDPOINT)
    //@QueryName()<-Filtro
    suspend fun getShows(): List<Show>
}