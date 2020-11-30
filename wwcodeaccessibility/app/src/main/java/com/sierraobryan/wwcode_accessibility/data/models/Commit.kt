package com.sierraobryan.wwcode_accessibility.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Author(
    @Json(name = "name") val name: String,
    @Json(name = "date") val date: String
)

@JsonClass(generateAdapter = true)
data class CommitDetails(
    @Json(name = "message") val message: String,
    @Json(name = "author") val author: Author)

@JsonClass(generateAdapter = true)
data class Commit(
    @Json(name = "commit") val commit: CommitDetails,
    @Json(name= "sha") val sha: String
) {

    val commitMessage: String
        get() = commit.message

    val author: String
        get() = commit.author.name

    val date: String
        get() = commit.author.date

}