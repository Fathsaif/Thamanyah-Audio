package com.example.thmanyahaudiotask.ui.home.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.thmanyahaudiotask.R
import com.example.thmanyahaudiotask.ui.theme.ThmanyahTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarTop(
    query: String,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit
) {

    var querySearch by remember { mutableStateOf(query) }

    TopAppBar(
        title = {
            TextField(
                value = querySearch,
                onValueChange = {
                    querySearch = it
                    onQueryChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.searching),
                        style = ThmanyahTheme.typography.bodyMedium
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = ThmanyahTheme.colors.surface,
                    focusedTextColor = ThmanyahTheme.colors.onBackground
                ),
                singleLine = true
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = ThmanyahTheme.colors.onBackground
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ThmanyahTheme.colors.background
        )
    )
}
