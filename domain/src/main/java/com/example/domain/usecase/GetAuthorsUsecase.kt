package com.example.domain.usecase

import com.example.domain.Repository
import javax.inject.Inject

class GetAuthorsUsecase @Inject constructor(val repository: Repository) {
    operator fun invoke(query: String) = repository.authors(query = query, maxResults = 20)
}