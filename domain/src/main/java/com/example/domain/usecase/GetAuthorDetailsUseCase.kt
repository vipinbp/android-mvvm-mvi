package com.example.domain.usecase

import com.example.domain.AuthorsRepository
import javax.inject.Inject

class GetAuthorDetailsUseCase @Inject constructor(val authorsRepository: AuthorsRepository) {
    suspend operator fun invoke(authorId: String) = authorsRepository.author(authorId = authorId)
}