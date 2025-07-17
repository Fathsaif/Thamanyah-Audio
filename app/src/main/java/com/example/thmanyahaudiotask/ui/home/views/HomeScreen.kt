package com.example.thmanyahaudiotask.ui.home.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.thmanyahaudiotask.ui.home.presenter.HomeUIStates
import com.example.thmanyahaudiotask.ui.home.presenter.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.uiState.collectAsState()
    when (state) {
        is HomeUIStates.Loading -> {
            // Show loading indicator
        }

        is HomeUIStates.Success -> {
            val sections = (state as HomeUIStates.Success).data
        }

        is HomeUIStates.Error -> {
            val errorMessage = (state as HomeUIStates.Error).message
            // Display error message
        }

        HomeUIStates.Empty -> {

        }
    }
}