package com.example.newsfeed.domain.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsArticle(
    val url: String,
    val sourceId: String?,
    val sourceName: String,
    val author: String?,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val publishedAt: String,
    val content: String?
): Parcelable
