package com.example.thmanyahaudiotask.ui.home.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thmanyahaudiotask.ui.home.presenter.models.ContentItemUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.ContentTypeUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionUi
import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionViewType
import com.example.thmanyahaudiotask.ui.theme.ThmanyahTheme

@Composable
fun SectionView(section: SectionUi, onItemClick: (ContentItemUi) -> Unit) {
    Column(modifier = Modifier.padding(vertical = ThmanyahTheme.spacing.spacing3)) {
        Text(
            text = section.name,
            style = ThmanyahTheme.typography.titleLarge,
            color = ThmanyahTheme.colors.onBackground,
            modifier = Modifier.padding(horizontal = ThmanyahTheme.spacing.spacing4)
        )

        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.spacing2))

        when (section.type) {
            SectionViewType.SQUARE,
            SectionViewType.BIG_SQUARE -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentPadding = PaddingValues(horizontal = ThmanyahTheme.spacing.spacing4)
                ) {
                    items(section.items) { item ->
                        ContentCard(item = item) { onItemClick(item) }
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

            SectionViewType.GRID_2_LINES -> {
                Column(modifier = Modifier.padding(horizontal = ThmanyahTheme.spacing.spacing4)) {
                    section.items.forEach { item ->
                        EpisodeRowCard(item = item) { onItemClick(item) }
                        Divider(color = ThmanyahTheme.colors.surface)
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
            duration = 3600L + index * 60,
            releaseDate = "2023-10-${index + 1}",
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
