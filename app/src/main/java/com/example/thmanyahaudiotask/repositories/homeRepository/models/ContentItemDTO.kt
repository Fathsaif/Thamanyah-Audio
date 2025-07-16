package com.example.thmanyahaudiotask.repositories.homeRepository.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentItemDTO(
    @SerialName("podcast_id")
    val podcastId: String? = null,

    @SerialName("episode_id")
    val episodeId: String? = null,

    @SerialName("audiobook_id")
    val audiobookId: String? = null,

    @SerialName("article_id")
    val articleId: String? = null,

    val name: String,

    val description: String? = null,

    @SerialName("avatar_url")
    val avatarUrl: String? = null,

    val duration: Long? = null,
    val language: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("author_name")
    val authorName: String? = null,

    @SerialName("episode_count")
    val episodeCount: Int? = null,

    val priority: Int? = null,

    @SerialName("popularityScore")
    val popularityScore: Int? = null,

    val score: Double? = null,

    @SerialName("podcast_name")
    val podcastName: String? = null,

    @SerialName("audio_url")
    val audioUrl: String? = null,

    @SerialName("episode_type")
    val episodeType: String? = null,

    @SerialName("season_number")
    val seasonNumber: Int? = null,

    val number: Int? = null
)

