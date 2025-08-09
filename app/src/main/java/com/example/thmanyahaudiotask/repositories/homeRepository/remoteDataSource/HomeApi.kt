package com.example.thmanyahaudiotask.repositories.homeRepository.remoteDataSource

import com.example.thmanyahaudiotask.repositories.homeRepository.models.HomeSectionsDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.models.SearchResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET("home_sections")
    suspend fun getHomeSections(
        @Query("page") page: Int? = null
    ): HomeSectionsDTO

    @GET("https://mock.apidog.com/m1/735111-711675-default/search")
    suspend fun searchHomeSections(
        @Query("query") query: String
    ): SearchResponseDTO
}
