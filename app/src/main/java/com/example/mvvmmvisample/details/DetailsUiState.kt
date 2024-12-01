package com.example.mvvmmvisample.details

data class DetailsUiState(
    val loading: Boolean = false,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = ""
)