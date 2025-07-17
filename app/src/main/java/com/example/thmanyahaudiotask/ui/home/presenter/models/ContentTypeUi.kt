package com.example.thmanyahaudiotask.ui.home.presenter.models

enum class ContentTypeUi(val value: String) {
    PODCAST("podcast"),
    EPISODE("episode"),
    AUDIO_BOOK("audio_book"),
    AUDIO_ARTICLE("audio_article"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String?): ContentTypeUi? {
            return entries.firstOrNull { it.value.equals(value, ignoreCase = true) } ?: UNKNOWN
        }
    }
}
