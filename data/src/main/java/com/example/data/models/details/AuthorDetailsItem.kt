package com.example.data.models.details

import com.google.gson.annotations.SerializedName

data class AuthorDetailsItem(
    @SerializedName("personal_name")
    val personalName: String,
    val key: String,
    @SerializedName("entity_type")
    val entityType: String,
    @SerializedName("birth_date")
    val birthDate: String,
    val links: List<Link>,
    @SerializedName("alternate_names")
    val alternateNames: List<String>,
    val name: String,
    @SerializedName("remote_ids")
    val remoteIds: RemoteIds,
    val type: Type2,
    val title: String,
    val bio: String,
    @SerializedName("fuller_name")
    val fullerName: String,
    val wikipedia: String,
    @SerializedName("source_records")
    val sourceRecords: List<String>,
    val photos: List<Long>,
    @SerializedName("latest_revision")
    val latestRevision: Long,
    val revision: Long,
    val created: Created,
    @SerializedName("last_modified")
    val lastModified: LastModified,
)

data class Link(
    val title: String,
    val url: String,
    val type: Type,
)

data class Type(
    val key: String,
)

data class RemoteIds(
    val viaf: String,
    val goodreads: String,
    val storygraph: String,
    val isni: String,
    val librarything: String,
    val amazon: String,
    val wikidata: String,
)

data class Type2(
    val key: String,
)

data class Created(
    val type: String,
    val value: String,
)

data class LastModified(
    val type: String,
    val value: String,
)