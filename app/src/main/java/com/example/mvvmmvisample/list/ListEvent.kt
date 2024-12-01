package com.example.mvvmmvisample.list

sealed class ListEvent {
    data class GoToDetails(val authorId: String) : ListEvent()
}