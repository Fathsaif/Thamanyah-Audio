package com.example.thmanyahaudiotask.ui.home.presenter.models

enum class ContentTypeUi {
    PODCAST,
    EPISODE,
    AUDIO_BOOK,
    AUDIO_ARTICLE,
    UNKNOWN;

    companion object {
        fun fromString(value: String): ContentTypeUi {
            return when (value.lowercase()) {
                "podcast" -> PODCAST
                "episode" -> EPISODE
                "audio_book" -> AUDIO_BOOK
                "audio_article" -> AUDIO_ARTICLE
                else -> UNKNOWN
            }
        }
    }
}
