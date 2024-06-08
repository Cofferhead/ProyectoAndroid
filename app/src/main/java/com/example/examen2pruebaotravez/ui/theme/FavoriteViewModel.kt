package com.example.examen2pruebaotravez.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.examen2pruebaotravez.Data.DataClass.Show
import com.example.examen2pruebaotravez.Data.Database.FavoriteShow
import com.example.examen2pruebaotravez.Repository.ShowRepository
import com.example.examen2pruebaotravez.ShowApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch


data class FavoriteUIState(
    val shows:List<FavoriteShow> = emptyList(),
    val showDetail: Show = Show(),
    val isLoading : Boolean = false,
    val error:String? = null
)
class FavoriteViewModel(
    private val showRepository: ShowRepository
) : ViewModel() {
    val uiState = mutableStateOf(FavoriteUIState())

    suspend fun <T> Flow<List<T>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()
    fun getFavoriteShows() {
        viewModelScope.launch(Dispatchers.IO) {

            uiState.value = uiState.value.copy(isLoading = true)
            try {
                val shows = showRepository.getFavoriteShows()
                val showList = shows.flattenToList()
                uiState.value = uiState.value.copy(shows = showList)
            } catch (e: Exception) {
                uiState.value = uiState.value.copy(error = e.message)
            } finally {
                uiState.value = uiState.value.copy(isLoading = false)
            }
        }
    }
    fun addFavorite(show:Show){
        viewModelScope.launch {
            try{
                showRepository.addFavoriteShow(show.toFavoriteShow())
            } catch (e: Exception){
                uiState.value = uiState.value.copy(error = e.message)
            }finally {
                uiState.value = uiState.value.copy(isLoading = false)
            }
        }
    }
    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as ShowApplication)
                val amphibiansRepository = application.container.showRepository
                FavoriteViewModel(showRepository = amphibiansRepository)
            }
        }
    }
}
