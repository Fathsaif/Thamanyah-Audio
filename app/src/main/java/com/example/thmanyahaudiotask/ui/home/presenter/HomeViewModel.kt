package com.example.thmanyahaudiotask.ui.home.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thmanyahaudiotask.domain.GetHomeSectionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeSectionsUseCase: GetHomeSectionsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUIStates>(HomeUIStates.Loading)
    val uiState: StateFlow<HomeUIStates> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            homeSectionsUseCase.invoke()
        }
    }
    // Define LiveData and other properties here

    // Example function to fetch data
    fun fetchData() {
        viewModelScope.launch {
            val result = homeSectionsUseCase.invoke()
            when (result) {
                is GetHomeSectionsUseCase.Result.Success -> {
                    _uiState.value = HomeUIStates.Success(result.sections)
                }

                is GetHomeSectionsUseCase.Result.EmptyDataError -> {
                    _uiState.value = HomeUIStates.Error("No data available")
                }

                GetHomeSectionsUseCase.Result.ServerError -> {
                    _uiState.value = HomeUIStates.Error("Server error occurred")
                }

                GetHomeSectionsUseCase.Result.NetworkError -> {
                    _uiState.value = HomeUIStates.Error("Network error occurred")
                }

                GetHomeSectionsUseCase.Result.FirstPageLoading -> {
                }

                GetHomeSectionsUseCase.Result.NextPageLoading -> {
                }
            }
        }
    }

}