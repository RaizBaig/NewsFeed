package com.example.newsfeed.data.db


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NewsArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
