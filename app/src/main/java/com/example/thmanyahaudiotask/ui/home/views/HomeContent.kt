package com.example.thmanyahaudiotask.ui.home.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionUi
import com.example.thmanyahaudiotask.ui.theme.ThmanyahTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    sections: List<SectionUi>,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
    isRefreshing: Boolean = false,
    isLoadingMore: Boolean = false,
    onLoadMore: () -> Unit = {}
) {
    val pullRefreshState = rememberPullToRefreshState()
    val listState = rememberLazyListState()

    val shouldLoadMore = remember {
        derivedStateOf {
            val info = listState.layoutInfo
            val lastVisible = info.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = info.totalItemsCount
            total > 0 && lastVisible >= total - 3 // threshold
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) onLoadMore()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .pullToRefresh(isRefreshing, pullRefreshState) { onRefresh() }
            .padding(horizontal = ThmanyahTheme.spacing.spacing4),
        state = listState
    ) {
        item { Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.spacing4)) }

        items(sections) { section ->
            SectionView(section = section)
            Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.spacing6))
        }

        if (isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(ThmanyahTheme.spacing.spacing4),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
