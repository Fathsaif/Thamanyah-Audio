package com.example.thmanyahaudiotask.repositories.homeRepository

import com.example.thmanyahaudiotask.repositories.homeRepository.models.HomeSectionsDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.remoteDataSource.HomeApi
import com.example.thmanyahaudiotask.utils.NetworkHelper
import com.example.thmanyahaudiotask.utils.Resource
import com.example.thmanyahaudiotask.utils.enums.ErrorStates

class HomeRepositoryImpl(
    private val homeApi: HomeApi,
) : HomeRepository {

    private var currentPage = INITIAL_PAGE

    override suspend fun getHomeSections(): Resource<HomeSectionsDTO> {
        return try {
            val response = homeApi.getHomeSections(
                page = currentPage
            )
            return Resource.Success(response)
        } catch (exception: Exception) {
            NetworkHelper.handleResponseException(
                exception = exception,
                networkError = Resource.Error(errorCode = ErrorStates.NETWORK_ERROR),
                serverError = Resource.Error(errorCode = ErrorStates.SERVER_ERROR)
            )
        }
    }

    override fun getPagination(): Int = currentPage

    override fun incrementPagination() {
        currentPage++
    }

    override fun resetPagination() {
        currentPage =
            INITIAL_PAGE
    }

    private companion object {
        private const val INITIAL_PAGE = 1
        private const val LIMIT = 10
    }
}
