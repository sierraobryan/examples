package com.example.hilt_simple.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "commit_data", primaryKeys = ["sha"])
data class RoomCommit(
    @ColumnInfo(name = "sha") val sha: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "commit_message") val commitMessage: String
)