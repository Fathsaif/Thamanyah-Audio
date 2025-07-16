package com.example.thmanyahaudiotask.repositories.homeRepository.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class HomeSectionsDTO(
    val sections: List<Section>,

    @SerialName("pagination")
    val pagination: Pagination? = null

)

@Serializable
data class Section(
    val name: String,
    val type: String,

    @SerialName("content_type")
    val contentType: String,

    val order: Int,
    val content: List<ContentItemDTO>
)

@Serializable
data class Pagination(
    @SerialName("next_page")
    val nextPage: String? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null
)

