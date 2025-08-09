package com.example.thamanyahaudiotask

import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepository
import com.example.thmanyahaudiotask.repositories.homeRepository.models.HomeSectionsDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.models.Pagination
import com.example.thmanyahaudiotask.repositories.homeRepository.models.SearchResponseDTO
import com.example.thmanyahaudiotask.utils.Resource

class FakeHomeRepository : HomeRepository {
    override suspend fun getHomeSections(): Resource<HomeSectionsDTO> {
        return Resource.Success(
            HomeSectionsDTO(sections = listOf(), pagination = Pagination())
        )
    }

    override suspend fun searchHomeSections(query: String): Resource<SearchResponseDTO> {
        return Resource.Success(
            SearchResponseDTO(sections = listOf())
        )
    }

    override fun getPagination(): Int = 1
    override fun incrementPagination() {}
    override fun resetPagination() {}

}
