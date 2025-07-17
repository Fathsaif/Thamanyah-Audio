package com.example.thmanyahaudiotask.repositories.homeRepository.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ContentItemDTO(
    @SerializedName("podcast_id")
    val podcastId: String? = null,

    @SerializedName("episode_id")
    val episodeId: String? = null,

    @SerializedName("audiobook_id")
    val audiobookId: String? = null,

    @SerializedName("article_id")
    val articleId: String? = null,

    val name: String,

    val description: String? = null,

    @SerializedName("avatar_url")
    val avatarUrl: String? = null,

    val duration: Long? = null,
    val language: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("author_name")
    val authorName: String? = null,

    @SerializedName("episode_count")
    val episodeCount: Int? = null,

    val priority: Int? = null,

    @SerializedName("popularityScore")
    val popularityScore: Int? = null,

    val score: Double? = null,

    @SerializedName("podcast_name")
    val podcastName: String? = null,

    @SerializedName("audio_url")
    val audioUrl: String? = null,

    @SerializedName("episode_type")
    val episodeType: String? = null,

    @SerializedName("season_number")
    val seasonNumber: Int? = null,

    val number: Int? = null
)

