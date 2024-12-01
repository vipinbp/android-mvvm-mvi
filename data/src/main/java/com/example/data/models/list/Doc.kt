package com.example.data.models.list

import com.google.gson.annotations.SerializedName

data class Doc(
    @SerializedName("alternate_names")
    val alternateNames: List<String>?,
    val key: String,
    val name: String,
    @SerializedName("top_subjects")
    val topSubjects: List<String>,
    @SerializedName("top_work")
    val topWork: String,
    val type: String,
    @SerializedName("work_count")
    val workCount: Long,
    @SerializedName("_version_")
    val version: Long,
)