package com.example.thmanyahaudiotask.ui.home.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thmanyahaudiotask.domain.GetHomeSectionsUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeSectionsUseCase: GetHomeSectionsUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            homeSectionsUseCase.invoke()
        }
    }
    // Define LiveData and other properties here

    // Example function to fetch data
    fun fetchData() {
        viewModelScope.launch {
            homeSectionsUseCase.invoke()
        }
    }

    // Other ViewModel methods can be added here) {
}