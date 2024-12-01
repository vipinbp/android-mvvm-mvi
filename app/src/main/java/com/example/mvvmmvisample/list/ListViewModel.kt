package com.example.mvvmmvisample.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AuthorList
import com.example.domain.usecase.GetAuthorsUseCase
import com.example.mvvmmvisample.common.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val getAuthorsUseCase: GetAuthorsUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ListUiEvent>()
    val uiEvent: SharedFlow<ListUiEvent> = _uiEvent.asSharedFlow()

    init {
        fetchAuthors()
    }

    private fun fetchAuthors() {
        viewModelScope.launch {

            _uiState.update {
                it.copy(state = ScreenState.Loading)
            }

            when (val result = getAuthorsUseCase(query = "jk")) {
                is AuthorList.Error -> _uiState.update {
                    it.copy(state = ScreenState.Error(result.message))
                }

                is AuthorList.Success -> _uiState.update {
                    it.copy(
                        listUiItem = result.authors.map { author ->
                            ListUiItem(
                                author.authorId,
                                author.name,
                                author.topSubjects,
                                author.profileImage
                            )
                        },
                        state = ScreenState.Success
                    )
                }
            }
        }
    }

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.GoToDetails -> {
                Log.d("ListViewModel", "onEvent: ${event.authorId}")
                //other viewmodel tasks to execute
                viewModelScope.launch {
                    _uiEvent.emit(ListUiEvent.GoToDetails(event.authorId))
                }
            }
        }
    }
}