package com.sogdev.rickandmortyapi.repository

import com.sogdev.rickandmortyapi.api.PersonajesApi
import com.sogdev.rickandmortyapi.model.ListaPersonajes
import javax.inject.Inject

class PersonajesRepository @Inject constructor(private val personajesApi: PersonajesApi) {
    suspend fun getPersonajes(currentPage: Int): ListaPersonajes{
        return personajesApi.getPersonajes(currentPage)
    }
}