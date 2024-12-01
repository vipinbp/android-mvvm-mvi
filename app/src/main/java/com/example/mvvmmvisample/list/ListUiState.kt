package com.example.mvvmmvisample.list

data class ListUiState(val loading: Boolean = false, val listUiItem: List<ListUiItem> = emptyList<ListUiItem>())