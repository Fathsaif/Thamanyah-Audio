package com.example.thmanyahaudiotask.repositories.homeRepository.models

import com.google.gson.annotations.SerializedName

data class SearchResponseDTO(
    val sections: List<SearchSectionDTO>,
)

data class SearchSectionDTO(
    val name: String,
    val type: String,
    val contentType: String,
    val order: String,
    val content: List<SearchContentItemDTO>
)

data class SearchContentItemDTO(
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

    val duration: String? = null,
    val language: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("author_name")
    val authorName: String? = null,

    @SerializedName("episode_count")
    val episodeCount: String? = null,

    val priority: String? = null,

    @SerializedName("popularityScore")
    val popularityScore: String? = null,

    val score: String? = null,

    @SerializedName("podcast_name")
    val podcastName: String? = null,

    @SerializedName("audio_url")
    val audioUrl: String? = null,

    @SerializedName("episode_type")
    val episodeType: String? = null,

    @SerializedName("season_number")
    val seasonNumber: String? = null,

    val number: String? = null
)
