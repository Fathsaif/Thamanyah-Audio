package com.example.thmanyahaudiotask.ui.home.presenter

import androidx.lifecycle.viewModelScope
import com.example.thmanyahaudiotask.domain.GetHomeSectionsUseCase
import com.example.thmanyahaudiotask.mvi_base.Intent
import com.example.thmanyahaudiotask.mvi_base.ViewModel
import com.example.thmanyahaudiotask.mvi_base.sideEffect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeSectionsUseCase: GetHomeSectionsUseCase
) : ViewModel<HomeState, HomeEvent>(startingState = HomeState()) {

    init {
        // kick off initial load
        process(HomeEvent.LoadInitial)
    }

    override fun HomeEvent.toIntent(): Intent<HomeState> = when (this) {
        HomeEvent.LoadInitial -> sideEffect {
            publish(HomeIntent.SetLoading(true))
            viewModelScope.launch {
                when (val result = homeSectionsUseCase()) {
                    is GetHomeSectionsUseCase.Result.Success -> {
                        publish(HomeIntent.SetData(result.sections))
                    }
                    is GetHomeSectionsUseCase.Result.EmptyDataError -> {
                        publish(HomeIntent.SetData(emptyList()))
                    }
                    GetHomeSectionsUseCase.Result.NetworkError -> {
                        publish(HomeIntent.SetError("Network error occurred"))
                    }
                    GetHomeSectionsUseCase.Result.ServerError -> {
                        publish(HomeIntent.SetError("Server error occurred"))
                    }
                    GetHomeSectionsUseCase.Result.Loading -> {
                        // no-op: we drive loading explicitly
                    }
                }
            }
        }

        HomeEvent.Refresh -> sideEffect {
            publish(HomeIntent.SetRefreshing(true))
            viewModelScope.launch {
                // optional: reset pagination before reloading (see note below)
                homeSectionsUseCase.resetPagination()
                when (val result = homeSectionsUseCase()) {
                    is GetHomeSectionsUseCase.Result.Success -> {
                        publish(HomeIntent.SetData(result.sections))
                    }
                    is GetHomeSectionsUseCase.Result.EmptyDataError -> {
                        publish(HomeIntent.SetData(emptyList()))
                    }
                    GetHomeSectionsUseCase.Result.NetworkError -> {
                        publish(HomeIntent.SetError("Network error occurred"))
                    }
                    GetHomeSectionsUseCase.Result.ServerError -> {
                        publish(HomeIntent.SetError("Server error occurred"))
                    }
                    GetHomeSectionsUseCase.Result.Loading -> Unit
                }
            }
        }

        HomeEvent.LoadNextPage -> sideEffect {
            // guard: avoid duplicate paging requests
            if (isLoadingMore || isLoading) return@sideEffect
            publish(HomeIntent.SetLoadingMore(true))
            viewModelScope.launch {
                when (val result = homeSectionsUseCase()) {
                    is GetHomeSectionsUseCase.Result.Success -> {
                        publish(HomeIntent.AppendData(result.sections))
                    }
                    is GetHomeSectionsUseCase.Result.EmptyDataError -> {
                        // no more items
                        publish(HomeIntent.SetLoadingMore(false))
                    }
                    GetHomeSectionsUseCase.Result.NetworkError -> {
                        // keep existing data; surface a non-blocking error if you want
                        publish(HomeIntent.SetLoadingMore(false))
                    }
                    GetHomeSectionsUseCase.Result.ServerError -> {
                        publish(HomeIntent.SetLoadingMore(false))
                    }
                    GetHomeSectionsUseCase.Result.Loading -> Unit
                }
            }
        }

        is HomeEvent.Retry -> sideEffect {
            when (from) {
                HomeEvent.RetrySource.INITIAL -> process(HomeEvent.LoadInitial)
                HomeEvent.RetrySource.PAGING -> process(HomeEvent.LoadNextPage)
            }
        }
    }
}
