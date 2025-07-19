package com.example.thmanyahaudiotask.ui.home.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thmanyahaudiotask.ui.home.presenter.models.ContentItemUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.ContentTypeUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionViewType
import com.example.thmanyahaudiotask.ui.theme.ThmanyahTheme

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun SectionView(section: SectionUi, onItemClick: (ContentItemUi) -> Unit = {}) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth * 0.7f
    Column(modifier = Modifier.padding(vertical = ThmanyahTheme.spacing.spacing3)) {
        Text(
            text = section.name,
            style = ThmanyahTheme.typography.titleLarge,
            color = ThmanyahTheme.colors.onBackground,
            modifier = Modifier.padding(horizontal = ThmanyahTheme.spacing.spacing4)
        )

        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.spacing4))

        when (section.type) {
            SectionViewType.SQUARE -> {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = ThmanyahTheme.spacing.spacing4),
                    horizontalArrangement = Arrangement.spacedBy(ThmanyahTheme.spacing.spacing4)
                ) {
                    items(section.items) { item ->
                        SquareContentView(
                            itemUi = item,
                            modifier = Modifier
                                .width(itemWidth)
                                .padding(horizontal = ThmanyahTheme.spacing.spacing2)
                        )
                    }
                }
            }

            SectionViewType.GRID_2_LINES -> {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentPadding = PaddingValues(horizontal = ThmanyahTheme.spacing.spacing4)
                ) {
                    items(section.items) { item ->
                        EpisodeRowCard(
                            item = item, modifier = Modifier
                                .width(itemWidth)
                        ) { onItemClick(item) }
                    }
                }
            }

            SectionViewType.HORIZONTAL_SCROLL -> {
                LazyRow(contentPadding = PaddingValues(horizontal = ThmanyahTheme.spacing.spacing4)) {
                    items(section.items) { item ->
                        ContentCard(item = item) { onItemClick(item) }
                    }
                }
            }

            SectionViewType.BIG_SQUARE -> {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = ThmanyahTheme.spacing.spacing2),
                    contentPadding = PaddingValues(horizontal = ThmanyahTheme.spacing.spacing4),
                    horizontalArrangement = Arrangement.spacedBy(ThmanyahTheme.spacing.spacing4)
                ) {
                    items(section.items) { item ->
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth()
                        ) {
                            BigSquareContent(
                                item = item,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                    }
                }
            }


            SectionViewType.UNKNOWN -> {
                Text(
                    text = "نوع عرض غير مدعوم",
                    style = ThmanyahTheme.typography.bodySmall,
                    modifier = Modifier.padding(ThmanyahTheme.spacing.spacing4),
                    color = ThmanyahTheme.colors.error
                )
            }
        }
    }
}

@Preview
@Composable
fun SectionViewPreview() {
    val sampleItems = List(10) { index ->
        ContentItemUi(
            id = index.toString(),
            name = "Item $index",
            avatarUrl = "https://via.placeholder.com/150",
            description = "Description for item $index",
            authorName = "Author $index",
            duration = "30:45",
            releaseDate = "2023-10-${index + 1}",
            episodesCount = "${index + 1} episodes",
        )
    }

    val sampleSection = SectionUi(
        name = "Sample Section",
        type = SectionViewType.HORIZONTAL_SCROLL,
        items = sampleItems,
        contentType = ContentTypeUi.PODCAST,
        order = 1
    )

    SectionView(section = sampleSection) {}

}
