package com.example.examen2pruebaotravez.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.examen2pruebaotravez.Data.DataClass.Show
import com.example.examen2pruebaotravez.Repository.ShowRepository
import com.example.examen2pruebaotravez.ShowApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


data class UIState(
    val shows:List<Show> = emptyList(),
    val showDetail:Show = Show(),
    val isLoading : Boolean = false,
    val error:String? = null
)
class ShowViewModel(
    private val showRepository: ShowRepository
) : ViewModel() {
    val uiState = mutableStateOf(UIState())
    fun searchShows (id:String){
        viewModelScope.launch(Dispatchers.IO) {

            uiState.value = uiState.value.copy(isLoading = true)
            try {
                val shows = showRepository.searchShow(id)
                uiState.value = uiState.value.copy(shows = shows)
            } catch (e: Exception) {
                uiState.value = uiState.value.copy(error = e.message)
            } finally {
                uiState.value = uiState.value.copy(isLoading = false)
            }
        }
    }


    fun getShows() {
        viewModelScope.launch(Dispatchers.IO) {

            uiState.value = uiState.value.copy(isLoading = true)
            try {
                val shows = showRepository.getShows()
                uiState.value = uiState.value.copy(shows = shows)
            } catch (e: Exception) {
                uiState.value = uiState.value.copy(error = e.message)
            } finally {
                uiState.value = uiState.value.copy(isLoading = false)
            }
        }
    }


    fun getShowDetail(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {

            uiState.value = uiState.value.copy(isLoading = true)
            try {
                val showDetail = showRepository.getShowDetail(id)
                uiState.value = uiState.value.copy(showDetail = showDetail)
            } catch (e: Exception) {
                uiState.value = uiState.value.copy(error = e.message)
            } finally {
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
                ShowViewModel(showRepository = amphibiansRepository)
            }
        }
    }
}
