package com.example.thmanyahaudiotask.di

import com.example.thmanyahaudiotask.domain.GetHomeSectionsUseCase
import com.example.thmanyahaudiotask.domain.SearchUseCase
import com.example.thmanyahaudiotask.repositories.RetrofitFactoryInterface
import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepository
import com.example.thmanyahaudiotask.repositories.homeRepository.HomeRepositoryImpl
import com.example.thmanyahaudiotask.repositories.homeRepository.remoteDataSource.HomeApi
import com.example.thmanyahaudiotask.ui.home.presenter.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    viewModel {
        HomeViewModel(get(),get())
    }

    factory {
        SearchUseCase(
            homeRepository = get(),
        )
    }

    factory {
        GetHomeSectionsUseCase(
            homeRepository = get(),
        )
    }
    factory<HomeRepository> {
        HomeRepositoryImpl(
            get(),
        )
    }
    factory {
        get<RetrofitFactoryInterface>().buildRetrofit()
            .create(HomeApi::class.java)
    }
}