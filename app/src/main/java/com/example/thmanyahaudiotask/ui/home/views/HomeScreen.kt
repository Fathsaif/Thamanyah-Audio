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
import androidx.navigation.NavHostController
import com.example.thmanyahaudiotask.R
import com.example.thmanyahaudiotask.navigation.Screens
import com.example.thmanyahaudiotask.ui.home.presenter.HomeUIStates.Empty
import com.example.thmanyahaudiotask.ui.home.presenter.HomeUIStates.Error
import com.example.thmanyahaudiotask.ui.home.presenter.HomeUIStates.Loading
import com.example.thmanyahaudiotask.ui.home.presenter.HomeUIStates.Success
import com.example.thmanyahaudiotask.ui.home.presenter.HomeViewModel
import com.example.thmanyahaudiotask.ui.home.views.skelton.HomeSkeletonScreen
import com.example.thmanyahaudiotask.ui.theme.ThmanyahTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()

    val isRefreshing by viewModel.refreshingState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        topBar = {
            HomeTopBar(
                onSearchClick = {
                    navController.navigate(
                        Screens.SEARCH.name
                    )
                },
            )
        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val currentState = state) {
                is Loading -> {
                    HomeSkeletonScreen()
                }

                is Success -> {
                    val sections = currentState.data
                    HomeContent(
                        sections, isRefreshing = isRefreshing,
                        onRefresh = {
                            viewModel.refresh()
                        })
                }

                is Error -> {
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

            }
        }
    }
}
