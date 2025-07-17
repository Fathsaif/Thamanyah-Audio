package com.example.thmanyahaudiotask.ui.home.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.thmanyahaudiotask.ui.home.presenter.models.ContentItemUi
import com.example.thmanyahaudiotask.ui.theme.ThmanyahTheme

@Composable
fun EpisodeRowCard(item: ContentItemUi, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ThmanyahTheme.spacing.spacing2)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = item.avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(ThmanyahTheme.spacing.spacing3))

        Column {
            Text(
                text = item.name,
                style = ThmanyahTheme.typography.bodyMedium,
                color = ThmanyahTheme.colors.onSurface,
                maxLines = 2
            )
            item.description?.let {
                Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.spacing1))
                Text(
                    text = it,
                    style = ThmanyahTheme.typography.bodySmall,
                    color = ThmanyahTheme.colors.onSurface,
                    maxLines = 2
                )
            }
        }
    }
}

@Preview
@Composable
fun EpisodeRowCardPreview() {
    EpisodeRowCard(
        item = ContentItemUi(
            id = "1",
            name = "Sample Episode",
            avatarUrl = "https://example.com/image.jpg",
            description = "This is a sample description for the episode.",
            duration = 1000L,
            releaseDate = "2023-10-01",
            authorName = "Author Name",
        )
    )

}
