package com.example.hilt_simple.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.hilt_simple.data.models.CommitDataDao
import com.example.hilt_simple.data.models.RoomCommit

@Database(
    entities = [RoomCommit::class],
    version = 1,
    exportSchema = false
)

abstract class MainDatabase: RoomDatabase() {
    abstract fun commitDataDao(): CommitDataDao
}

object DatabaseMigrations {
    val migrations: List<Migration> = listOf()
}