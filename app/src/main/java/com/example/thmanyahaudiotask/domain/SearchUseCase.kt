package com.example.thmanyahaudiotask.domain

import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepository
import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.toUiModel
import com.example.thmanyahaudiotask.utils.Resource

class SearchUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(query: String): Result {
        return when (val result = homeRepository.searchHomeSections(query)) {
            is Resource.Error -> {
                when (result.errorCode) {
                    com.example.thmanyahaudiotask.utils.enums.ErrorStates.NETWORK_ERROR -> Result.NetworkError
                    com.example.thmanyahaudiotask.utils.enums.ErrorStates.MISSING_DATA_ERROR -> Result.EmptyDataError
                    else -> Result.ServerError
                }
            }

            is Resource.Loading -> {
                Result.Loading
            }

            is Resource.Success -> {
                result.data?.let { data ->
                    if (data.sections.isNotEmpty()) {
                        Result.Success(
                            sections = data.sections.map { it.toUiModel() }
                        )
                    } else {
                        Result.EmptyDataError
                    }
                } ?: Result.EmptyDataError
            }
        }
    }

    sealed interface Result {
        data object Loading : Result
        data object NetworkError : Result
        data object ServerError : Result
        data object EmptyDataError : Result
        data class Success(
            val sections: List<SectionUi>
        ) : Result
    }
}

