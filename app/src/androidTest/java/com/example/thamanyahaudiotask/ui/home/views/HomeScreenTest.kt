package com.example.thamanyahaudiotask.ui.home.views

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.thamanyahaudiotask.FakeHomeRepository
import com.example.thmanyahaudiotask.MainActivity
import com.example.thmanyahaudiotask.domain.GetHomeSectionsUseCase
import com.example.thmanyahaudiotask.domain.SearchUseCase
import com.example.thmanyahaudiotask.ui.home.presenter.HomeViewModel
import com.example.thmanyahaudiotask.ui.home.views.HomeScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var testViewModel: HomeViewModel

    @Before
    fun setup() {
        val fakeRepository = FakeHomeRepository()
        val useCase = GetHomeSectionsUseCase(fakeRepository)
        val searchUseCase = SearchUseCase(fakeRepository)

        testViewModel = HomeViewModel(useCase, searchUseCase)

        loadKoinModules(
            module {
                viewModel { testViewModel }
            }
        )
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun homeScreen_initialLoading_showsSkeleton() {
        composeTestRule.setContent {
            HomeScreen()
        }

        composeTestRule
            .onAllNodesWithTag("ShimmerItem")
            .assertCountEquals(7)
    }

    @Test
    fun homeScreen_displaysSearchIcon() {
        composeTestRule
            .onNodeWithContentDescription("Search Icon")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_clickSearch_displaysSearchField() {
        runCatching {
            composeTestRule.setContent {
                // Launch your app or screen
                HomeScreen()
            }

            composeTestRule
                .onNodeWithTag("Search Icon")
                .performClick()

            composeTestRule
                .onNodeWithTag("SearchTextField")
                .assertIsDisplayed()
        }.onFailure { e ->
            e.printStackTrace()
            throw e
        }
    }
}
