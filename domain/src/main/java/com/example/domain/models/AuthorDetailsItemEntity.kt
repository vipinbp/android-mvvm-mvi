package com.example.domain.models

data class AuthorDetailsItemEntity(val authorId: String, val name: String, val description: String, val profileImage: String)

sealed class AuthorDetails {
    data class Success(val authorDetails: AuthorDetailsItemEntity) : AuthorDetails()
    data class Error(val message: String) : AuthorDetails()
}