package com.sogdev.rickandmortyapi.api

import com.sogdev.rickandmortyapi.model.ListaPersonajes
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonajesApi {
    @GET("character")
    suspend fun getPersonajes(@Query("page") page: Int): ListaPersonajes
}