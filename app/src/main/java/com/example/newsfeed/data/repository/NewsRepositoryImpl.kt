package com.example.newsfeed.data.repository

import android.util.Log
import com.example.newsfeed.data.api.NewsApiService
import com.example.newsfeed.data.db.NewsDao
import com.example.newsfeed.data.mapper.NewsMapper
import com.example.newsfeed.domain.model.NewsArticle
import com.example.newsfeed.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApiService,
    private val dao: NewsDao
) : NewsRepository {

    override suspend fun getNews(query: String, limit: Int, offset: Int): List<NewsArticle> {
        try {
            // Fetch from API (you might want to remove `from` param or handle paging with API if available)
            val response = api.getNews(
                query = query,
                from = "2025-05-24",
                apiKey = "9efb1ed0b980404f8006c9bbca65ff0b"
            )

            if (response.isSuccessful && response.body() != null) {
                val articlesFromApi = NewsMapper.fromDto(response.body()!!)
                // Insert all articles from API to DB (no pagination here)
                dao.insertArticles(articlesFromApi.map(NewsMapper::toEntity))
            } else {
                Log.e("NewsRepository", "API error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("NewsRepository", "Exception: ${e.message}")
        }

        // Return paginated and filtered results from local DB
        return dao.getArticlesPaged(limit, offset).map(NewsMapper::fromEntity)
    }
}


