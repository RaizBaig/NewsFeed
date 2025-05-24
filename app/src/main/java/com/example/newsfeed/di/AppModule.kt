package com.example.newsfeed.di

import android.content.Context
import androidx.room.Room
import com.example.newsfeed.data.api.NewsApiService
import com.example.newsfeed.data.db.NewsDao
import com.example.newsfeed.data.db.NewsDatabase
import com.example.newsfeed.data.repository.NewsRepositoryImpl
import com.example.newsfeed.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl(): String = "https://newsapi.org/"

    @Provides
    @Singleton
    fun provideRetrofit(): NewsApiService =
        Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase =
        Room.databaseBuilder(context, NewsDatabase::class.java, "news_db").build()

    @Provides
    fun provideNewsDao(database: NewsDatabase): NewsDao = database.newsDao()

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApiService, dao: NewsDao): NewsRepository =
        NewsRepositoryImpl(api, dao)
}
