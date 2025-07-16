package com.example.thmanyahaudiotask.domain

import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepository
import com.example.thmanyahaudiotask.repositories.homeRepository.models.ContentItemDTO
import com.example.thmanyahaudiotask.utils.Resource
import com.example.thmanyahaudiotask.utils.enums.ErrorStates

private const val INITIAL_PAGE = 1

class GetHomeSectionsUseCase(
    private val homeRepository: HomeRepository
) {

    // Define the function to get home sections
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
                result.data?.let { sections ->
                    /*  if (stores.stores.isNotEmpty()) {
                          val isNextPageAvailable = stores.pagination.currentPage == stores.pagination.totalPages
                          if (isNextPageAvailable) storesRepository.incrementPagination()
                          Result.Success(
                              isNextPageAvailable = isNextPageAvailable,
                              stores = stores.stores
                          )
                      } else {
                          Result.EmptyDataError
                      }*/
                    Result.Success(
                        isNextPageAvailable = sections.sections.isNotEmpty(),
                        sections = sections.sections[0].content
                    )

                } ?: Result.EmptyDataError
            }

            is Resource.Error -> {
                when (result.errorCode) {
                    ErrorStates.NETWORK_ERROR -> Result.NetworkError
                    ErrorStates.MISSING_DATA_ERROR -> Result.EmptyDataError
                    else -> Result.ServerError
                }
            }

            else -> Result.EmptyDataError
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
            val sections: List<ContentItemDTO>
        ) : Result
    }
}