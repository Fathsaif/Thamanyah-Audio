package com.example.thmanyahaudiotask.ui.home.presenter.models

import com.example.thmanyahaudiotask.repositories.homeRepository.models.ContentItemDTO
import com.example.thmanyahaudiotask.repositories.homeRepository.models.SectionDTO

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
    val duration: Long?,
    val releaseDate: String?,
    val audioUrl: String? = null
)

fun SectionDTO.toUiModel(): SectionUi {
    return SectionUi(
        name = name,
        type = SectionViewType.fromRawType(type),
        contentType = ContentTypeUi.fromString(contentType),
        order = order,
        items = content.map { it.toUiModel(contentType) }
    )
}

fun ContentItemDTO.toUiModel(contentType: String): ContentItemUi {
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
        avatarUrl = avatarUrl,
        authorName = authorName,
        duration = duration,
        releaseDate = releaseDate,
        audioUrl = audioUrl
    )
}
