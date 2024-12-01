package com.example.mvvmmvisample.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetAuthorDetailsUsecase
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
class DetailsViewModel @Inject constructor(val getAuthorDetailsUsecase: GetAuthorDetailsUsecase): ViewModel() {
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()
    init {
        fetchAuthorDetails("OL23919A")
    }

    private fun fetchAuthorDetails(authorId: String) {
        viewModelScope.launch {
            getAuthorDetailsUsecase(authorId)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    // handle exception
                    Log.d("DetailsViewModel", "fetchAuthorDetails: $e")
                }
                .collect { author ->
                    // list of users from the network
                    author?.let {
                        _uiState.update {
                            it.copy(
                                title = author.name,
                                description = author.name,
                                imageUrl = author.name
                            )
                        }
                    }
                }
        }
    }
}