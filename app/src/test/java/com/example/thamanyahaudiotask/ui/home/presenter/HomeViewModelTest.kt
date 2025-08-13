package com.example.thamanyahaudiotask.ui.home.presenter

import app.cash.turbine.test
import com.example.thmanyahaudiotask.domain.GetHomeSectionsUseCase
import com.example.thmanyahaudiotask.domain.SearchUseCase
import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepository
import com.example.thmanyahaudiotask.repositories.homeRepository.models.ContentItemDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.models.HomeSectionsDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.models.SectionDTO
import com.example.thmanyahaudiotask.ui.home.presenter.HomeEvent
import com.example.thmanyahaudiotask.ui.home.presenter.HomeState
import com.example.thmanyahaudiotask.ui.home.presenter.HomeViewModel
import com.example.thmanyahaudiotask.ui.home.presenter.models.ContentItemUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.ContentTypeUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionViewType
import com.example.thmanyahaudiotask.utils.Resource
import com.example.thmanyahaudiotask.utils.enums.ErrorStates
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class HomeViewModelTest {
    @get:Rule
    val main = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel

    private lateinit var homeSectionsUseCase: GetHomeSectionsUseCase

    private lateinit var searchUseCase: SearchUseCase

    @Mock
    private lateinit var homeRepository: HomeRepository

    private var closeable: AutoCloseable? = null

    private val page1Dto = homeSectionsDto(sections = listOf(sectionDto("Page1")))
    private val page2Dto = homeSectionsDto(sections = listOf(sectionDto("Page2")))

    private val page1Ui = listOf(sectionUi("Page1"))
    private val page2Ui = listOf(sectionUi("Page2"))

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        homeSectionsUseCase = GetHomeSectionsUseCase(homeRepository)
        searchUseCase = SearchUseCase(homeRepository)
        viewModel = HomeViewModel(homeSectionsUseCase)
    }

    @Test
    fun fetchData() = runTest {
        `when`(homeRepository.getHomeSections()).thenReturn(
            Resource.Success(
                data = page1Dto
            )
        )
        viewModel.process(HomeEvent.LoadInitial)

        viewModel.state.test {
            val initial = awaitItem()
            assertEquals(HomeState(), initial)

            main.dispatcher.scheduler.advanceUntilIdle()

            val loading = awaitItem()
            assertTrue(loading.isLoading)

            val success = awaitItem()
            assertFalse(success.isLoading)
            assertEquals(page1Ui, success.sections)
            assertNull(success.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun refresh_replaces_data() = runTest {
        `when`(homeRepository.getHomeSections()).thenReturn(
            Resource.Success(page1Dto),
            Resource.Success(page2Dto)
        )

        viewModel.process(HomeEvent.LoadInitial)

        viewModel.state.test {
            awaitItem()
            main.dispatcher.scheduler.advanceUntilIdle()

            awaitItem()
            val first = awaitItem()
            assertEquals(page1Ui, first.sections)

            viewModel.process(HomeEvent.Refresh)
            main.dispatcher.scheduler.advanceUntilIdle()

            val refreshing = awaitItem()
            assertTrue(refreshing.isRefreshing)

            val replaced = awaitItem()
            assertFalse(replaced.isRefreshing)
            assertEquals(page2Ui, replaced.sections)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun load_next_page_appends() = runTest {
        `when`(homeRepository.getHomeSections()).thenReturn(
            Resource.Success(page1Dto),
            Resource.Success(page2Dto)
        )

        viewModel.process(HomeEvent.LoadInitial)

        viewModel.state.test {
            awaitItem()
            main.dispatcher.scheduler.advanceUntilIdle()

            awaitItem()
            val first = awaitItem()
            assertEquals(page1Ui, first.sections)

            // Trigger paging
            viewModel.process(HomeEvent.LoadNextPage)
            main.dispatcher.scheduler.advanceUntilIdle()

            val paging = awaitItem()
            assertTrue(paging.isLoadingMore)

            val appended = awaitItem()
            assertFalse(appended.isLoadingMore)
            assertEquals(page1Ui + page2Ui, appended.sections)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun network_error_sets_error() = runTest {
        `when`(homeRepository.getHomeSections()).thenReturn(
            Resource.Error(errorCode = ErrorStates.NETWORK_ERROR)
        )

        viewModel.process(HomeEvent.LoadInitial)
        main.dispatcher.scheduler.advanceUntilIdle()

        val s = viewModel.state.value
        assertNotNull(s.error)
        assertTrue(s.error.contains("Network", ignoreCase = true))
        assertFalse(s.isLoading)
    }

    @After
    fun tearDown() {
        closeable?.close()
    }

    // ---------- helpers ----------
    private fun homeSectionsDto(sections: List<SectionDTO>) = HomeSectionsDTO(
        sections = sections
    )

    private fun sectionDto(name: String) = SectionDTO(
        name = name,
        type = "square",
        contentType = "podcast",
        order = 0,
        content = listOf(
            ContentItemDTO(
                podcastId = "1",
                episodeId = null,
                audiobookId = null,
                articleId = null,
                name = "Item $name",
                description = null,
                avatarUrl = null,
                authorName = "Author",
                duration = 600,
                releaseDate = "2025-01-01",
                audioUrl = null,
                episodeCount = 1
            )
        )
    )

    private fun sectionUi(name: String) = SectionUi(
        name = name,
        type = SectionViewType.SQUARE,
        contentType = ContentTypeUi.PODCAST,
        order = 0,
        items = listOf(
            ContentItemUi(
                id = "1",
                name = "Item $name",
                description = null,
                avatarUrl = null,
                authorName = "Author",
                duration = "10m",
                releaseDate = "",
                audioUrl = null,
                episodesCount = "1"
            )
        )
    )
}
