package com.example.thmanyahaudiotask.ui.home.presenter

import com.example.thmanyahaudiotask.mvi_base.Intent
import com.example.thmanyahaudiotask.ui.home.presenter.models.SectionUi

sealed interface HomeIntent : Intent<HomeState> {

    data class SetLoading(val value: Boolean) : HomeIntent {
        override fun reduce(old: HomeState) = old.copy(
            isLoading = value,
            error = if (value) null else old.error
        )
    }

    data class SetRefreshing(val value: Boolean) : HomeIntent {
        override fun reduce(old: HomeState) = old.copy(isRefreshing = value)
    }

    data class SetLoadingMore(val value: Boolean) : HomeIntent {
        override fun reduce(old: HomeState) = old.copy(isLoadingMore = value)
    }

    data class SetData(val sections: List<SectionUi>) : HomeIntent {
        override fun reduce(old: HomeState) = old.copy(
            sections = sections,
            isLoading = false,
            isRefreshing = false,
            error = null
        )
    }

    data class AppendData(val sections: List<SectionUi>) : HomeIntent {
        override fun reduce(old: HomeState) = old.copy(
            sections = old.sections + sections,
            isLoadingMore = false,
            error = null
        )
    }

    data class SetError(val message: String) : HomeIntent {
        override fun reduce(old: HomeState) = old.copy(
            isLoading = false,
            isRefreshing = false,
            isLoadingMore = false,
            error = message
        )
    }

    data object ClearError : HomeIntent {
        override fun reduce(old: HomeState) = old.copy(error = null)
    }
}
