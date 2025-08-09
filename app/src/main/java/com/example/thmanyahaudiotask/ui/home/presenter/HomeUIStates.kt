package com.example.thmanyahaudiotask.ui.home.presenter

import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionUi

data class HomeState(
    val sections: List<SectionUi> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null
) {
    val isEmpty: Boolean get() = !isLoading && error == null && sections.isEmpty()
}
