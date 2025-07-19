package com.example.thmanyahaudiotask.di

import com.example.thmanyahaudiotask.domain.SearchUseCase
import com.example.thmanyahaudiotask.ui.search.presenter.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel { SearchViewModel(get()) }
    factory { SearchUseCase(get()) }

}