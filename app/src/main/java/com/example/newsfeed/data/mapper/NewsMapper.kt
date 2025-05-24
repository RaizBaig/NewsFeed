package com.example.newsfeed.data.mapper

import com.example.newsfeed.data.db.NewsArticleEntity
import com.example.newsfeed.data.model.NewsDto
import com.example.newsfeed.domain.model.NewsArticle

object NewsMapper {

    fun fromDto(dto: NewsDto): List<NewsArticle> {
        return dto.articles.map { article ->
            NewsArticle(
                sourceId = article.source.id,
                sourceName = article.source.name,
                author = article.author,
                title = article.title,
                description = article.description,
                url = article.url,
                imageUrl = article.urlToImage,
                publishedAt = article.publishedAt,
                content = article.content
            )
        }
    }

    fun toEntity(article: NewsArticle): NewsArticleEntity {
        return NewsArticleEntity(
            sourceName = article.sourceName,
            sourceId = article.sourceId,
            author = article.author,
            title = article.title,
            description = article.description,
            url = article.url,
            urlToImage = article.imageUrl,
            publishedAt = article.publishedAt,
            content = article.content
        )
    }

    fun fromEntity(entity: NewsArticleEntity): NewsArticle {
        return NewsArticle(
            sourceId = entity.sourceId,
            sourceName = entity.sourceName,
            author = entity.author,
            title = entity.title,
            description = entity.description,
            url = entity.url,
            imageUrl = entity.urlToImage,
            publishedAt = entity.publishedAt,
            content = entity.content
        )
    }
}
