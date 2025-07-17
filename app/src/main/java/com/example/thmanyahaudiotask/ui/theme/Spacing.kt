package com.example.thmanyahaudiotask.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val spacing1: Dp = 4.dp,
    val spacing2: Dp = 8.dp,
    val spacing3: Dp = 12.dp,
    val spacing4: Dp = 16.dp,
    val spacing5: Dp = 20.dp,
    val spacing6: Dp = 24.dp,
    val spacing7: Dp = 30.dp,
    val spacing8: Dp = 64.dp,
    val spacing9: Dp = 72.dp,
    val spacing10: Dp = 80.dp,
    val spacing11: Dp = 88.dp,
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }
