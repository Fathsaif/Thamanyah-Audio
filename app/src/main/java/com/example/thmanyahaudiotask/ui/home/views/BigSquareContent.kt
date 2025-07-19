package com.example.thmanyahaudiotask.ui.home.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.thmanyahaudiotask.ui.home.presenter.models.ContentItemUi
import com.example.thmanyahaudiotask.ui.theme.ThmanyahTheme

@Composable
fun BigSquareContent(
    item: ContentItemUi,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(ThmanyahTheme.spacing.spacing3),
        modifier = modifier
            .fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = ThmanyahTheme.colors.primaryContainer)
    ) {

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = item.avatarUrl,
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .padding(ThmanyahTheme.spacing.spacing2)
                    .aspectRatio(1.5f)
                    .clip(RoundedCornerShape(ThmanyahTheme.spacing.spacing3))
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1.2f)
                    .padding(ThmanyahTheme.spacing.spacing4)
            ) {
                Text(
                    text = item.name,
                    style = ThmanyahTheme.typography.titleLarge,
                    color = ThmanyahTheme.colors.onSurface,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(ThmanyahTheme.spacing.spacing1))
                Row {
                    Text(
                        text = item.duration ?: "0:00",
                        style = ThmanyahTheme.typography.bodySmall,
                        color = ThmanyahTheme.colors.onSurface,
                    )
                    Spacer(modifier = Modifier.width(ThmanyahTheme.spacing.spacing1))
                    Text(
                        text = item.releaseDate ?: " Unknown ",
                        style = ThmanyahTheme.typography.bodySmall,
                        color = ThmanyahTheme.colors.onSurface,
                    )
                }

            }
        }
    }
}