package com.example.thmanyahaudiotask.ui.home.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun ContentCard(
    item: ContentItemUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(160.dp)
            .padding(ThmanyahTheme.spacing.spacing2)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = item.avatarUrl,
            contentDescription = item.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(ThmanyahTheme.spacing.spacing3))
                .background(ThmanyahTheme.colors.surface)
        )

        Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.spacing2))

        Text(
            text = item.name,
            style = ThmanyahTheme.typography.bodyMedium,
            color = ThmanyahTheme.colors.onSurface,
            maxLines = 2
        )
    }
}

@Preview
@Composable
fun ContentCardPreview() {
    ContentCard(
        item = ContentItemUi(
            id = "1",
            name = "Sample Content",
            avatarUrl = "https://via.placeholder.com/150",
            description = "This is a sample content description.",
            duration = "1000L",
            authorName = "Author Name",
            releaseDate = "2023-10-01",
            episodesCount = "10",
        ),
        onClick = {}
    )
}
