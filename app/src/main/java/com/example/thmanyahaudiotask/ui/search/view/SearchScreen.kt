package com.example.thmanyahaudiotask.ui.search.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.thmanyahaudiotask.R
import com.example.thmanyahaudiotask.ui.home.views.HomeContent
import com.example.thmanyahaudiotask.ui.home.views.SearchBarTop
import com.example.thmanyahaudiotask.ui.home.views.skelton.HomeSkeletonScreen
import com.example.thmanyahaudiotask.ui.search.presenter.SearchUIState
import com.example.thmanyahaudiotask.ui.search.presenter.SearchViewModel
import com.example.thmanyahaudiotask.ui.theme.ThmanyahTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(navController: NavHostController) {
    val viewModel: SearchViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()
    val searchQuery = viewModel.searchQuery.collectAsState().value

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        topBar = {
            SearchBarTop(searchQuery, onQueryChange = {
                viewModel.onQueryChanged(it)
            }, onBackClick = {
                navController.navigateUp()
            })

        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val currentState = state) {
                is SearchUIState.Searching -> {
                    HomeSkeletonScreen()
                }

                is SearchUIState.Success -> {
                    val sections = currentState.data
                    HomeContent(sections)
                }

                is SearchUIState.Error -> {
                    val errorMessage = currentState.message
                    Text(
                        text = errorMessage,
                        style = ThmanyahTheme.typography.bodyLarge,
                        color = ThmanyahTheme.colors.error,
                        modifier = Modifier
                            .padding(ThmanyahTheme.spacing.spacing4)
                            .align(
                                Alignment.Center
                            )
                    )
                }

                SearchUIState.Empty -> {
                    Text(
                        text = stringResource(R.string.no_search_results),
                        style = ThmanyahTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(ThmanyahTheme.spacing.spacing4)
                            .align(
                                Alignment.Center
                            )
                    )
                }

                is SearchUIState.Init -> {
                    Text(
                        text = stringResource(R.string.searching),
                        style = ThmanyahTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(ThmanyahTheme.spacing.spacing4)
                            .align(
                                Alignment.Center
                            )
                    )
                }

            }
        }

    }
}