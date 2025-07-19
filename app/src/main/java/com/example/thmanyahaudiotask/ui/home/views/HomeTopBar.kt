package com.example.thmanyahaudiotask.ui.home.views

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.thmanyahaudiotask.R
import com.example.thmanyahaudiotask.ui.theme.ThmanyahTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onSearchClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = ThmanyahTheme.typography.titleLarge,
                color = ThmanyahTheme.colors.onBackground
            )
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search Icon",
                    tint = ThmanyahTheme.colors.onBackground
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ThmanyahTheme.colors.background
        )
    )
}

