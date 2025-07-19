package com.example.thmanyahaudiotask.ui.home.presenter.models

import com.example.thmanyahaudiotask.repositories.homeRepository.models.ContentItemDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.models.SearchContentItemDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.models.SearchSectionDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.models.SectionDTO
import com.example.thmanyahaudiotask.utils.formatDuration
import com.example.thmanyahaudiotask.utils.formatTimeAgo

data class SectionUi(
    val name: String,
    val type: SectionViewType,
    val contentType: ContentTypeUi,
    val order: Int,
    val items: List<ContentItemUi>
)

data class ContentItemUi(
    val id: String,
    val name: String,
    val description: String?,
    val avatarUrl: String?,
    val authorName: String?,
    val duration: String?,
    val releaseDate: String?,
    val audioUrl: String? = null,
    val episodesCount: String,
)

fun SectionDTO.toUiModel(): SectionUi {
    return SectionUi(
        name = name,
        type = SectionViewType.fromRawType(type),
        contentType = ContentTypeUi.fromString(contentType) ?: ContentTypeUi.UNKNOWN,
        order = order,
        items = content.map { it.toUiModel(contentType) }
    )
}

fun ContentItemDTO.toUiModel(contentType: String?): ContentItemUi {
    val id = when (ContentTypeUi.fromString(contentType)) {
        ContentTypeUi.PODCAST -> podcastId ?: ""
        ContentTypeUi.EPISODE -> episodeId ?: ""
        ContentTypeUi.AUDIO_BOOK -> audiobookId ?: ""
        ContentTypeUi.AUDIO_ARTICLE -> articleId ?: ""
        else -> ""
    }
    return ContentItemUi(
        id = id,
        name = name,
        description = description,
        avatarUrl = this.avatarUrl,
        authorName = authorName,
        duration = formatDuration(duration),
        releaseDate = formatTimeAgo(releaseDate),
        audioUrl = audioUrl,
        episodesCount = episodeCount?.toString() ?: "0"
    )
}

fun SearchSectionDTO.toUiModel(): SectionUi {
    return SectionUi(
        name = name,
        type = SectionViewType.fromRawType(type),
        contentType = ContentTypeUi.fromString(contentType) ?: ContentTypeUi.UNKNOWN,
        order = order.toIntOrNull() ?: 0,
        items = content.map { it.toUiModel(contentType) }
    )
}

fun SearchContentItemDTO.toUiModel(contentType: String?): ContentItemUi {
    val id = when (ContentTypeUi.fromString(contentType)) {
        ContentTypeUi.PODCAST -> podcastId ?: ""
        ContentTypeUi.EPISODE -> episodeId ?: ""
        ContentTypeUi.AUDIO_BOOK -> audiobookId ?: ""
        ContentTypeUi.AUDIO_ARTICLE -> articleId ?: ""
        else -> ""
    }
    return ContentItemUi(
        id = id,
        name = name,
        description = description,
        avatarUrl = this.avatarUrl,
        authorName = authorName,
        duration = duration,
        releaseDate = releaseDate,
        audioUrl = audioUrl,
        episodesCount = episodeCount ?: "0"
    )
}
