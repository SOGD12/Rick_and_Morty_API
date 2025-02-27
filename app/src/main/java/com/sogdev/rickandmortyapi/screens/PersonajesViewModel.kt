package com.sogdev.rickandmortyapi.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogdev.rickandmortyapi.model.Result
import com.sogdev.rickandmortyapi.repository.PersonajesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonajesViewModel @Inject constructor(
    private val repo: PersonajesRepository
): ViewModel() {
    private val _state = MutableStateFlow(emptyList<Result>())
    val state: StateFlow<List<Result>>
        get() = _state

    private var currentPage = 1
    private var isLoading = false
    private var hasMorePages = true

//    init{
//        viewModelScope.launch {
//            _state.value = repo.getPersonajes().results
//        }
//    }

    init{
        LoadingCharacter()
    }
    fun LoadingCharacter() {
        if (isLoading || !hasMorePages) return
        isLoading = true
        viewModelScope.launch {
            try{
                val response = repo.getPersonajes(currentPage)
                _state.value += response.results

                currentPage++
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                isLoading = false
            }
        }
    }
}

