package com.example.domain.usecase

import com.example.domain.AuthorsRepository
import com.example.domain.models.AuthorList
import javax.inject.Inject

class GetAuthorsUseCase @Inject constructor(private val authorsRepository: AuthorsRepository) {
    suspend operator fun invoke(query: String): AuthorList = authorsRepository.authors(query = query)
}