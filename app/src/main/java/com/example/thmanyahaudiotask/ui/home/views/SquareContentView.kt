package com.example.thmanyahaudiotask.ui.home.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.thmanyahaudiotask.R
import com.example.thmanyahaudiotask.ui.home.presenter.models.ContentItemUi
import com.example.thmanyahaudiotask.ui.theme.ThmanyahTheme

@Composable
fun SquareContentView(
    itemUi: ContentItemUi,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = itemUi.avatarUrl,
            contentDescription = itemUi.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1.5f)
                .clip(RoundedCornerShape(ThmanyahTheme.spacing.spacing3))
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(ThmanyahTheme.spacing.spacing4)
        ) {
            Text(
                text = itemUi.name,
                style = ThmanyahTheme.typography.titleLarge,
                color = ThmanyahTheme.colors.onSurface,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.spacing1))

            Text(
                text = stringResource(R.string.episode_count, itemUi.episodesCount),
                style = ThmanyahTheme.typography.bodySmall,
                color = ThmanyahTheme.colors.onSurface,
            )

        }

    }

}