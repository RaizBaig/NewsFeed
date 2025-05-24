package com.example.newsfeed.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsfeed.domain.repository.NewsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class NewsSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repo: NewsRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            repo.getNews(
                "us",0,0)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
