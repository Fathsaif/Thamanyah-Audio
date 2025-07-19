package com.example.thmanyahaudiotask.ui.home.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionUi
import com.example.thmanyahaudiotask.ui.theme.ThmanyahTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    sections: List<SectionUi>,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {  },
    isRefreshing: Boolean = false
) {
    val pullRefreshState = rememberPullToRefreshState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .pullToRefresh(isRefreshing, pullRefreshState) {
                onRefresh()
            }
            .padding(horizontal = ThmanyahTheme.spacing.spacing4)
    ) {
        item {
            Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.spacing4))
        }

        items(sections) { section ->
            SectionView(section = section)
            Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.spacing6))
        }
    }
}