package com.example.thmanyahaudiotask.domain

import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepository
import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.toUiModel
import com.example.thmanyahaudiotask.utils.Resource
import com.example.thmanyahaudiotask.utils.enums.ErrorStates

private const val INITIAL_PAGE = 1

class GetHomeSectionsUseCase(
    private val homeRepository: HomeRepository
) {

    suspend operator fun invoke(): Result {
        val result = homeRepository.getHomeSections()

        return when (result) {
            is Resource.Loading -> {
                if (homeRepository.getPagination() == INITIAL_PAGE) {
                    Result.FirstPageLoading
                } else {
                    Result.NextPageLoading
                }
            }

            is Resource.Success -> {
                result.data?.let { data ->
                    if (data.sections.isNotEmpty()) {
                        //val isNextPageAvailable = data.pagination?.nextPage == data.pagination.totalPages
                        // if (isNextPageAvailable) homeRepository.incrementPagination()
                        Result.Success(
                            isNextPageAvailable = data.sections.isNotEmpty(),
                            sections = data.sections.map {
                                it.toUiModel()
                            }
                        )
                    } else {
                        Result.EmptyDataError
                    }

                } ?: Result.EmptyDataError
            }

            is Resource.Error -> {
                when (result.errorCode) {
                    ErrorStates.NETWORK_ERROR -> Result.NetworkError
                    ErrorStates.MISSING_DATA_ERROR -> Result.EmptyDataError
                    else -> Result.ServerError
                }
            }
        }
    }

    sealed interface Result {
        data object FirstPageLoading : Result
        data object NextPageLoading : Result
        data object NetworkError : Result
        data object ServerError : Result
        data object EmptyDataError : Result
        data class Success(
            var isNextPageAvailable: Boolean = false,
            val sections: List<SectionUi>
        ) : Result
    }
}
