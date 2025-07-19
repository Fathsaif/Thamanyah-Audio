package com.example.thamanyahaudiotask.ui.home

import com.example.thmanyahaudiotask.domain.GetHomeSectionsUseCase
import com.example.thmanyahaudiotask.domain.SearchUseCase
import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepository
import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepositoryImpl
import com.example.thmanyahaudiotask.ui.home.presenter.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.mockito.Mockito.mock

val homeTestModule = module {
    viewModel { HomeViewModel(get()) }
    single<HomeRepository> { mock(HomeRepositoryImpl::class.java) }
    single { GetHomeSectionsUseCase(get()) }
    single { SearchUseCase(get()) }
}