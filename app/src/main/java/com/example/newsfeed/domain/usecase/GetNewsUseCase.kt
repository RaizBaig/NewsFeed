package com.example.newsfeed.domain.usecase

import com.example.newsfeed.domain.model.NewsArticle
import com.example.newsfeed.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(query: String , limit: Int , offset:Int): List<NewsArticle> {
        return repository.getNews(query , limit , offset)
    }
}
