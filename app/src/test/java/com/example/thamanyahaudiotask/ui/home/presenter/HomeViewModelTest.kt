package com.example.thamanyahaudiotask.ui.home.presenter

import app.cash.turbine.test
import com.example.thamanyahaudiotask.domain.homeFakeSectionsDTO
import com.example.thamanyahaudiotask.domain.homeFakeUiModel
import com.example.thmanyahaudiotask.domain.GetHomeSectionsUseCase
import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepository
import com.example.thmanyahaudiotask.utils.Resource
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    private lateinit var homeSectionsUseCase: GetHomeSectionsUseCase

    @Mock
    private lateinit var homeRepository: HomeRepository

    private var closeable: AutoCloseable? = null

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        homeSectionsUseCase = GetHomeSectionsUseCase(homeRepository)
    }

    @After
    fun tearDown() {
        closeable?.close()
    }

    @Test
    fun `load initial data success updates state`() = runTest {
        `when`(homeRepository.getHomeSections()).thenReturn(
            Resource.Success(
                data = homeFakeSectionsDTO
            )
        )

        viewModel = HomeViewModel(homeSectionsUseCase)

        viewModel.state.test {
            assertEquals(HomeState(), awaitItem())
            assertEquals(HomeState(isLoading = true), awaitItem())
            assertEquals(
                HomeState(sections = listOf(homeFakeUiModel)),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `refresh emits refreshing then data`() = runTest {
        `when`(homeRepository.getHomeSections()).thenReturn(
            Resource.Success(homeFakeSectionsDTO),
            Resource.Success(homeFakeSectionsDTO)
        )

        viewModel = HomeViewModel(homeSectionsUseCase)

        viewModel.state.test {
            awaitItem() // initial
            awaitItem() // loading
            awaitItem() // initial data

            viewModel.process(HomeEvent.Refresh)

            val refreshing = awaitItem()
            assertTrue(refreshing.isRefreshing)
            assertEquals(listOf(homeFakeUiModel), refreshing.sections)

            val refreshed = awaitItem()
            assertFalse(refreshed.isRefreshing)
            assertEquals(listOf(homeFakeUiModel), refreshed.sections)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `load next page appends data`() = runTest {
        `when`(homeRepository.getHomeSections()).thenReturn(
            Resource.Success(homeFakeSectionsDTO),
            Resource.Success(homeFakeSectionsDTO)
        )

        viewModel = HomeViewModel(homeSectionsUseCase)

        viewModel.state.test {
            awaitItem() // initial
            awaitItem() // loading
            val firstData = awaitItem()
            assertEquals(listOf(homeFakeUiModel), firstData.sections)

            viewModel.process(HomeEvent.LoadNextPage)

            val loadingMore = awaitItem()
            assertTrue(loadingMore.isLoadingMore)
            assertEquals(listOf(homeFakeUiModel), loadingMore.sections)

            val appended = awaitItem()
            assertFalse(appended.isLoadingMore)
            assertEquals(listOf(homeFakeUiModel, homeFakeUiModel), appended.sections)

            cancelAndIgnoreRemainingEvents()
        }
    }
}

