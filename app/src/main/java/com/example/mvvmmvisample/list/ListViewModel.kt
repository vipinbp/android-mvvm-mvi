package com.example.mvvmmvisample.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetAuthorsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(val getAuthorsUsecase: GetAuthorsUsecase): ViewModel() {

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init {
        fetchAuthors()
    }

    private fun fetchAuthors() {
        viewModelScope.launch {
            getAuthorsUsecase("jk")
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    // handle exception
                    Log.d("ListViewModel", "fetchAuthors: $e")
                }
                .collect { authors ->
                    // list of users from the network
                    authors?.let {
                        _uiState.update {
                            it.copy(listUiItem = authors.map { author ->
                                ListUiItem(
                                    author.name,
                                    author.name,
                                    author.name
                                )
                            })
                        }
                    }
                }
        }
    }
}