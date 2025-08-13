package com.example.thamanyahaudiotask.domain

import com.example.thmanyahaudiotask.domain.GetHomeSectionsUseCase
import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepository
import com.example.thmanyahaudiotask.repositories.homeRepository.models.ContentItemDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.models.HomeSectionsDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.models.Pagination
import com.example.thmanyahaudiotask.repositories.homeRepository.models.SectionDTO
import com.example.thmanyahaudiotask.ui.home.presenter.models.ContentItemUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.ContentTypeUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionViewType
import com.example.thmanyahaudiotask.utils.Resource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetHomeSectionsUseCaseTest {

    private lateinit var homeUseCase: GetHomeSectionsUseCase

    @Mock
    private lateinit var homeRepository: HomeRepository

    private var closeable: AutoCloseable? = null

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        homeUseCase = GetHomeSectionsUseCase(homeRepository)
    }

    @Test
    fun `test invoke returns data`() = runTest {
        // Arrange
        `when`(homeRepository.getHomeSections()).thenReturn(
            Resource.Success(
                data = homeFakeSectionsDTO
            )
        )
        // Act
        val result = homeUseCase.invoke()

        // Assert
        assert(result is GetHomeSectionsUseCase.Result.Success)
        assertEquals(
            homeFakeUiModel,
            (result as GetHomeSectionsUseCase.Result.Success).sections.first()
        )
    }

    @Test
    fun `test invoke returns empty data error`() = runTest {
        // Arrange
        `when`(homeRepository.getHomeSections()).thenReturn(
            Resource.Success(
                data = HomeSectionsDTO(sections = emptyList(), pagination = null)
            )
        )
        // Act
        val result = homeUseCase.invoke()

        // Assert
        assert(result is GetHomeSectionsUseCase.Result.EmptyDataError)
    }

    @Test
    fun `test invoke returns server error`() = runTest {
        // Arrange
        `when`(homeRepository.getHomeSections()).thenReturn(
            Resource.Error(errorCode = com.example.thmanyahaudiotask.utils.enums.ErrorStates.SERVER_ERROR)
        )
        // Act
        val result = homeUseCase.invoke()

        // Assert
        assert(result is GetHomeSectionsUseCase.Result.ServerError)
    }

    @Test
    fun `test invoke returns network error`() = runTest {
        // Arrange
        `when`(homeRepository.getHomeSections()).thenReturn(
            Resource.Error(errorCode = com.example.thmanyahaudiotask.utils.enums.ErrorStates.NETWORK_ERROR)
        )
        // Act
        val result = homeUseCase.invoke()

        // Assert
        assert(result is GetHomeSectionsUseCase.Result.NetworkError)
    }

}

val homeFakeSectionsDTO = HomeSectionsDTO(
    sections = listOf(
        SectionDTO(
            name = "Popular",
            type = "big square",
            contentType = "podcast",
            order = 1,
            content = listOf(
                ContentItemDTO(
                    podcastId = "id1",
                    name = "Podcast One",
                    avatarUrl = "https://example.com/image.jpg",
                    description = "Test description",
                    authorName = "Author One",
                    duration = 3600L,
                    releaseDate = "2023-10-01",
                )
            )
        )
    ),
    pagination = Pagination(
        nextPage = "/home_sections?page=2",
        totalPages = 10
    )
)
val homeFakeUiModel = SectionUi(
    name = "Popular",
    type = SectionViewType.BIG_SQUARE,
    contentType = ContentTypeUi.PODCAST,
    order = 1,
    items = listOf(
        ContentItemUi(
            id = "id1",
            name = "Podcast One",
            description = "Test description",
            avatarUrl = "https://example.com/image.jpg",
            authorName = "Author One",
            duration = "3600L" ,
            releaseDate = "2023-10-01",
            episodesCount = "9"
        )
    )
)