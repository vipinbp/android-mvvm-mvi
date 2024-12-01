package com.example.mvvmmvisample.list

sealed class ListUiEvent {
    data class GoToDetails(val authorId: String) : ListUiEvent()
}