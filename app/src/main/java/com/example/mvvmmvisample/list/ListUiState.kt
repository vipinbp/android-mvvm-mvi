package com.example.mvvmmvisample.list

import com.example.mvvmmvisample.common.ScreenState

data class ListUiState(
    val state: ScreenState = ScreenState.Loading,
    val listUiItem: List<ListUiItem> = emptyList<ListUiItem>()
)