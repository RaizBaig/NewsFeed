package com.example.newsfeed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.domain.model.NewsArticle
import com.example.newsfeed.domain.usecase.GetNewsUseCase
import com.example.newsfeed.util.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _news = MutableStateFlow<ResponseHandler<List<NewsArticle>>>(ResponseHandler.Loading)
    val news: StateFlow<ResponseHandler<List<NewsArticle>>> = _news

    private var offset = 0
    private val limit = 10
    private val allNews = mutableListOf<NewsArticle>()

    private var isLoading = false
    private var hasMoreData = true

    fun fetchNews(query: String, isInitialLoad: Boolean = false) {
        if (isLoading || !hasMoreData) return

        if (isInitialLoad) {
            offset = 0
            allNews.clear()
            hasMoreData = true
        }

        isLoading = true
        _news.value = ResponseHandler.Loading

        viewModelScope.launch {
            try {
                val articles = getNewsUseCase(query, limit, offset)
                if (articles.isNotEmpty()) {
                    allNews.addAll(articles)
                    offset += limit
                    _news.value = ResponseHandler.Success(allNews.toList())
                } else {
                    hasMoreData = false
                }
            } catch (e: Exception) {
                _news.value = ResponseHandler.Error(e.message ?: "Unknown error")
            } finally {
                isLoading = false
            }
        }
    }
}
