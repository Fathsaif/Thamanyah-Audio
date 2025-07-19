package com.example.thmanyahaudiotask.ui.home.presenter.models

enum class SectionViewType {
    SQUARE,
    BIG_SQUARE,
    GRID_2_LINES,
    HORIZONTAL_SCROLL,
    UNKNOWN;

    companion object {
        fun fromRawType(type: String): SectionViewType {
            return when (type) {
                "square" -> SQUARE
                "big square", "big_square" -> BIG_SQUARE
                "2_lines_grid" -> GRID_2_LINES
                "queue" -> HORIZONTAL_SCROLL
                else -> SQUARE
            }
        }
    }
}
