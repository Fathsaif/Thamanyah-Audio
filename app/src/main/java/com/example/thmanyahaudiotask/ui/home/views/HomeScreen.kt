package com.example.thmanyahaudiotask.ui.home.views

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
import com.example.thmanyahaudiotask.R
import com.example.thmanyahaudiotask.ui.home.presenter.HomeUIStates.Empty
import com.example.thmanyahaudiotask.ui.home.presenter.HomeUIStates.Error
import com.example.thmanyahaudiotask.ui.home.presenter.HomeUIStates.Loading
import com.example.thmanyahaudiotask.ui.home.presenter.HomeUIStates.SearchMode
import com.example.thmanyahaudiotask.ui.home.presenter.HomeUIStates.Success
import com.example.thmanyahaudiotask.ui.home.presenter.HomeViewModel
import com.example.thmanyahaudiotask.ui.home.views.skelton.HomeSkeletonScreen
import com.example.thmanyahaudiotask.ui.theme.ThmanyahTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        topBar = {
            if (state is SearchMode) {
                SearchBarTop((state as SearchMode).query, onQueryChange = {
                    viewModel.onQueryChanged(it)
                }, onBackClick = {
                    viewModel.onSearchBackClick()
                })
            } else {
                HomeTopBar(
                    onSearchClick = { viewModel.onSearchClick() },
                )
            }
        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state) {
                is Loading -> {
                    HomeSkeletonScreen()
                }

                is Success -> {
                    val sections = (state as Success).data
                    HomeContent(sections)
                }

                is Error -> {
                    val errorMessage = (state as Error).message
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

                Empty -> {
                    Text(
                        text = stringResource(R.string.no_data_available),
                        style = ThmanyahTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(ThmanyahTheme.spacing.spacing4)
                            .align(
                                Alignment.Center
                            )
                    )
                }

                is SearchMode -> {
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
