package com.example.thmanyahaudiotask.repositories.homeRepository

import com.example.thmanyahaudiotask.repositories.homeRepository.models.HomeSectionsDTO
import com.example.thmanyahaudiotask.utils.Resource

interface HomeRepository {
    suspend fun getHomeSections(): Resource<HomeSectionsDTO>
    fun getPagination(): Int
    fun incrementPagination()
    fun resetPagination()
}