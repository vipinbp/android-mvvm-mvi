package com.example.data.models

import com.example.data.models.details.AuthorDetailsItem
import com.example.data.models.list.AuthorItem
import com.example.domain.models.AuthorDetailsItemEntity
import com.example.domain.models.AuthorItemEntity

fun AuthorItem.toAuthorItemEntity(): List<AuthorItemEntity> {
    return this.docs.map {
        AuthorItemEntity(
            authorId = it.key,
            name = it.name,
            topSubjects = it.topSubjects?.joinToString(separator = ", ", prefix = "Top subjects: ")
                ?: "",
            profileImage = constructImageUrl(it.key)
        )
    }
}

fun AuthorDetailsItem.toAuthorDetailsItemEntity(authorId: String): AuthorDetailsItemEntity {
    return AuthorDetailsItemEntity(
        authorId = this.key,
        name = this.name,
        description = this.bio,
        profileImage = constructImageUrl(authorId)
    )
}

fun constructImageUrl(authorId: String) = "https://covers.openlibrary.org/a/olid/$authorId.jpg"