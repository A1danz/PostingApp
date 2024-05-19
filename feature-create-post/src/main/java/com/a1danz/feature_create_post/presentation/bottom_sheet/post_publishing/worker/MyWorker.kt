package com.a1danz.feature_create_post.presentation.bottom_sheet.post_publishing.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class MyWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        // Симулируем фоновую работу
        delay(3000)
        // Сообщаем о завершении работы
        return Result.success()
    }
}