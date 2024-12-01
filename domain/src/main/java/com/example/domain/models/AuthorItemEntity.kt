package com.example.domain.models

data class AuthorItemEntity(val authorId: String, val name: String, val topSubjects: String, val profileImage: String)

sealed class AuthorList {
    data class Success(val authors: List<AuthorItemEntity>) : AuthorList()
    data class Error(val message: String) : AuthorList()
}