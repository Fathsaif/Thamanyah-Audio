package com.example.thamanyahaudiotask.repositories.homeRepository

import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepository
import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepositoryImpl
import com.example.thmanyahaudiotask.repositories.homeRepository.models.HomeSectionsDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.models.Pagination
import com.example.thmanyahaudiotask.repositories.homeRepository.remoteDataSource.HomeApi
import com.example.thmanyahaudiotask.utils.Resource
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class HomeRepositoryImplTest {

    @Mock
    private lateinit var homeApi: HomeApi
    private lateinit var homeRepository: HomeRepository

    private var closeable: AutoCloseable? = null

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        homeRepository = HomeRepositoryImpl(homeApi)
    }

    @Test
    fun `getHomeSections return Success`() = runTest {
        `when`(homeApi.getHomeSections(1)).thenReturn(
            homeFakeSectionsDTO
        )

        val result = homeRepository.getHomeSections()
        assert(result is Resource.Success)
        Assertions.assertEquals(homeFakeSectionsDTO, result.data)
    }

    @Test
    fun `getHomeSections return Error on Exception`() = runTest {
        `when`(homeApi.getHomeSections(1)).thenThrow(RuntimeException("Server error"))

        val result = homeRepository.getHomeSections()

        assert(result is Resource.Error)
    }


    @Test
    fun searchHomeSections() {
    }

    @After
    fun tearDown() {
        closeable?.close()
    }

    val homeFakeSectionsDTO = HomeSectionsDTO(
        sections = listOf(),
        pagination = Pagination()
    )

    val homeFakeWrongSectionsDTO = HomeSectionsDTO(
        sections = listOf(),
        pagination = Pagination(
            nextPage = "2",
            totalPages = 5
        )
    )
}