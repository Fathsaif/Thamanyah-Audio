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
import com.example.thmanyahaudiotask.ui.home.presenter.HomeEvent
import com.example.thmanyahaudiotask.ui.home.presenter.HomeViewModel
import com.example.thmanyahaudiotask.ui.home.views.skelton.HomeSkeletonScreen
import com.example.thmanyahaudiotask.ui.theme.ThmanyahTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    val vm: HomeViewModel = koinViewModel()
    val state by vm.state.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        topBar = {
            HomeTopBar(
                onSearchClick = { navController.navigate(Screens.SEARCH.name) },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                state.isLoading -> {
                    HomeSkeletonScreen()
                }

                state.error != null -> {
                    Text(
                        text = state.error ?: "",
                        style = ThmanyahTheme.typography.bodyLarge,
                        color = ThmanyahTheme.colors.error,
                        modifier = Modifier
                            .padding(ThmanyahTheme.spacing.spacing4)
                            .align(Alignment.Center)
                    )
                }

                state.isEmpty -> {
                    Text(
                        text = stringResource(R.string.no_data_available),
                        style = ThmanyahTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(ThmanyahTheme.spacing.spacing4)
                            .align(Alignment.Center)
                    )
                }

                else -> {
                    HomeContent(
                        sections = state.sections,
                        isRefreshing = state.isRefreshing,
                        isLoadingMore = state.isLoadingMore,
                        onRefresh = { vm.process(HomeEvent.Refresh) },
                        onLoadMore = { vm.process(HomeEvent.LoadNextPage) }
                    )
                }
            }
        }
    }
}
