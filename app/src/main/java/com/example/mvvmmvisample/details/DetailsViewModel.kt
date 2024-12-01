package com.example.mvvmmvisample.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AuthorDetails
import com.example.domain.usecase.GetAuthorDetailsUsecase
import com.example.mvvmmvisample.common.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val getAuthorDetailsUsecase: GetAuthorDetailsUsecase
) : ViewModel() {
    val authorId: String = checkNotNull(savedStateHandle["authorId"])
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    init {
        fetchAuthorDetails(authorId)
    }

    private fun fetchAuthorDetails(authorId: String) {
        viewModelScope.launch {

            _uiState.update {
                it.copy(state = ScreenState.Loading)
            }

            when (val result = getAuthorDetailsUsecase(authorId = authorId)) {
                is AuthorDetails.Error -> _uiState.update {
                    it.copy(state = ScreenState.Error(result.message))
                }

                is AuthorDetails.Success -> _uiState.update {
                    it.copy(
                        title = result.authorDetails.name,
                        description = result.authorDetails.description,
                        imageUrl = result.authorDetails.profileImage,
                        state = ScreenState.Success
                    )
                }
            }
        }
    }
}