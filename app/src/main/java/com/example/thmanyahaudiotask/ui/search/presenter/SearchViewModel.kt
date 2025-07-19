package com.example.thmanyahaudiotask.ui.search.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thmanyahaudiotask.domain.SearchUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<SearchUIState>(SearchUIState.Init)
    val uiState: StateFlow<SearchUIState> = _uiState.asStateFlow()
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(DEBOUNCE_TIME)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isNotBlank()) {
                        _uiState.value = SearchUIState.Searching(query)
                        performSearch(query)
                    }
                }
        }
    }

    fun onQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private suspend fun performSearch(query: String) {
        when (val result = searchUseCase(query)) {
            is SearchUseCase.Result.Success -> {
                if (result.sections.isNotEmpty()) {
                    _uiState.value = SearchUIState.Success(result.sections)
                } else {
                    _uiState.value = SearchUIState.Empty
                }
            }

            SearchUseCase.Result.EmptyDataError -> {
                _uiState.value = SearchUIState.Empty
            }

            SearchUseCase.Result.NetworkError -> {
                _uiState.value = SearchUIState.Error("Network error occurred")
            }

            SearchUseCase.Result.ServerError -> {
                _uiState.value = SearchUIState.Error("Server error occurred")
            }

            SearchUseCase.Result.Loading -> {
                _uiState.value = SearchUIState.Searching(_searchQuery.value)
            }
        }
    }

    companion object {
        const val DEBOUNCE_TIME = 200L
    }
}