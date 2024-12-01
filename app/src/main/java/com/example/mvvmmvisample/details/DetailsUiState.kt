package com.example.mvvmmvisample.details

import com.example.mvvmmvisample.common.ScreenState

data class DetailsUiState(
    val state: ScreenState = ScreenState.Loading,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = ""
)