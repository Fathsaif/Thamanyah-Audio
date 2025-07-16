package com.example.thmanyahaudiotask.repositories.homeRepository.remoteDataSource

import com.example.thmanyahaudiotask.repositories.homeRepository.models.HomeSectionsDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET("home_sections")
    suspend fun getHomeSections(
        @Query("page") page: Int? = null
    ): HomeSectionsDTO
}
