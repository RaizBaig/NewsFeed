package com.example.newsfeed.domain.repository

import com.example.newsfeed.domain.model.NewsArticle

interface NewsRepository {
    suspend fun getNews(query: String, limit: Int, offset: Int): List<NewsArticle>
}
