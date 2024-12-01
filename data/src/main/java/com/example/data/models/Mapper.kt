package com.example.data.models

import com.example.data.models.details.AuthorDetailsItem
import com.example.data.models.list.AuthorItem
import com.example.domain.models.AuthorDetailsItemEntity
import com.example.domain.models.AuthorItemEntity

fun AuthorItem.toAuthorItemEntity(): List<AuthorItemEntity> {
    return this.docs.map {
        AuthorItemEntity(authorId = it.key, name = it.name)
    }
}
fun AuthorDetailsItem.toAuthorDetailsItemEntity(): AuthorDetailsItemEntity {
    return AuthorDetailsItemEntity(authorId = this.key, name = this.name)
}