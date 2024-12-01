package com.example.domain.usecase

import com.example.domain.Repository
import javax.inject.Inject

class GetAuthorDetailsUsecase @Inject constructor(val repository: Repository) {
    operator fun invoke(authorId: String) = repository.author(authorId = authorId)
}