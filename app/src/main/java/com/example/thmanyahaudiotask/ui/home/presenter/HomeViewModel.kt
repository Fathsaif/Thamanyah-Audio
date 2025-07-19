package com.example.thmanyahaudiotask.ui.home.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thmanyahaudiotask.domain.GetHomeSectionsUseCase
import com.example.thmanyahaudiotask.domain.SearchUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeSectionsUseCase: GetHomeSectionsUseCase,
    private val searchUseCase: SearchUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUIStates>(HomeUIStates.Loading)
    val uiState: StateFlow<HomeUIStates> = _uiState.asStateFlow()
    private val searchQuery = MutableStateFlow("")

    init {
        fetchData()
        observeSearchQuery()
    }

    fun fetchData() {
        viewModelScope.launch {
            val result = homeSectionsUseCase.invoke()
            when (result) {
                GetHomeSectionsUseCase.Result.Loading -> {
                    _uiState.value = HomeUIStates.Loading
                }

                is GetHomeSectionsUseCase.Result.Success -> {
                    _uiState.value = HomeUIStates.Success(result.sections)
                }

                is GetHomeSectionsUseCase.Result.EmptyDataError -> {
                    _uiState.value = HomeUIStates.Empty
                }

                GetHomeSectionsUseCase.Result.ServerError -> {
                    _uiState.value = HomeUIStates.Error("Server error occurred")
                }

                GetHomeSectionsUseCase.Result.NetworkError -> {
                    _uiState.value = HomeUIStates.Error("Network error occurred")
                }

            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(DEBOUNCE_TIME)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isNotBlank()) {
                        _uiState.value = HomeUIStates.Loading
                        fetchData()
                    }
                }
        }
    }

    fun onSearchClick() {
        _uiState.value = HomeUIStates.SearchMode()
    }

    fun onQueryChanged(query: String) {
        _uiState.value = HomeUIStates.SearchMode(query)
        searchQuery.value = query
    }

    fun onSearchBackClick() {
        fetchData()
    }

    private suspend fun performSearch(query: String) {
        when (val result = searchUseCase(query)) {
            is SearchUseCase.Result.Success -> {
                if (result.sections.isNotEmpty()) {
                    _uiState.value = HomeUIStates.Success(result.sections)
                } else {
                    _uiState.value = HomeUIStates.Empty
                }
            }

            SearchUseCase.Result.EmptyDataError -> {
                _uiState.value = HomeUIStates.Empty
            }

            SearchUseCase.Result.NetworkError -> {
                _uiState.value = HomeUIStates.Error("Network error occurred")
            }

            SearchUseCase.Result.ServerError -> {
                _uiState.value = HomeUIStates.Error("Server error occurred")
            }

            SearchUseCase.Result.Loading -> {
                _uiState.value = HomeUIStates.Loading
            }
        }
    }

    companion object {
        const val DEBOUNCE_TIME = 200L
    }
}