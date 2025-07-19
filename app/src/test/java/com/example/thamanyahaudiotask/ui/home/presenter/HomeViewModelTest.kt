package com.example.thamanyahaudiotask.ui.home.presenter

import app.cash.turbine.test
import com.example.thamanyahaudiotask.domain.homeFakeSectionsDTO
import com.example.thamanyahaudiotask.domain.homeFakeUiModel
import com.example.thmanyahaudiotask.domain.GetHomeSectionsUseCase
import com.example.thmanyahaudiotask.domain.SearchUseCase
import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepository
import com.example.thmanyahaudiotask.ui.home.presenter.HomeUIStates
import com.example.thmanyahaudiotask.ui.home.presenter.HomeViewModel
import com.example.thmanyahaudiotask.utils.Resource
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    private lateinit var homeSectionsUseCase: GetHomeSectionsUseCase

    private lateinit var searchUseCase: SearchUseCase

    @Mock
    private lateinit var homeRepository: HomeRepository

    private var closeable: AutoCloseable? = null

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        homeSectionsUseCase = GetHomeSectionsUseCase(homeRepository)
        searchUseCase = SearchUseCase(homeRepository)
        viewModel = HomeViewModel(homeSectionsUseCase, searchUseCase)
    }

    @Test
    fun fetchData() = runTest {
        // Simulate a successful fetch
        `when`(homeRepository.getHomeSections()).thenReturn(
            Resource.Success(
                data = homeFakeSectionsDTO
            )
        )
        viewModel.fetchData()

        viewModel.uiState.test {
            // Verify that the UI state is updated to Success with the expected data
            assertEquals(HomeUIStates.Loading, awaitItem())
            assertEquals(HomeUIStates.Success(listOf(homeFakeUiModel)), awaitItem())
        }
    }

    @Test
    fun onSearchClick() = runTest {
        // Simulate a search click

        viewModel.onSearchClick()

        // Verify that the search query is set correctly
        viewModel.uiState.test {
            assertEquals(HomeUIStates.SearchMode(""), awaitItem())
        }
    }

    @Test
    fun onQueryChanged() = runTest {
        // Simulate a query change
        val query = "test query"
        viewModel.onQueryChanged(query)

        // Verify that the UI state is updated to SearchMode with the new query
        viewModel.uiState.test {
            assertEquals(HomeUIStates.SearchMode(query), awaitItem())
        }
    }

    @After
    fun tearDown() {
        closeable?.close()
    }
}