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
                    }
                }
            }
        }

        HomeEvent.Refresh -> sideEffect {
            publish(HomeIntent.SetRefreshing(true))
            viewModelScope.launch {
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
            if (isLoadingMore || isLoading) return@sideEffect
            publish(HomeIntent.SetLoadingMore(true))
            viewModelScope.launch {
                when (val result = homeSectionsUseCase()) {
                    is GetHomeSectionsUseCase.Result.Success -> {
                        publish(HomeIntent.AppendData(result.sections))
                    }
                    is GetHomeSectionsUseCase.Result.EmptyDataError -> {
                        publish(HomeIntent.SetLoadingMore(false))
                    }
                    GetHomeSectionsUseCase.Result.NetworkError -> {
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
