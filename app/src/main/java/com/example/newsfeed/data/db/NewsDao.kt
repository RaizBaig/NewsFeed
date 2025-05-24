package com.example.newsfeed.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_articles ORDER BY publishedAt DESC")
    suspend fun getAllArticles(): List<NewsArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<NewsArticleEntity>)

    @Query("SELECT * FROM news_articles ORDER BY publishedAt DESC LIMIT :limit OFFSET :offset")
    suspend fun getArticlesPaged(limit: Int, offset: Int): List<NewsArticleEntity>
}
