package com.example.thmanyahaudiotask.repositories.homeRepository

import com.example.thmanyahaudiotask.repositories.homeRepository.models.HomeSectionsDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.models.SearchResponseDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.models.SearchSectionDTO
import com.example.thmanyahaudiotask.utils.Resource

interface HomeRepository {
    suspend fun getHomeSections(): Resource<HomeSectionsDTO>

    suspend fun searchHomeSections(query: String): Resource<SearchResponseDTO>

    fun getPagination(): Int
    fun incrementPagination()
    fun resetPagination()
}