package com.github.csandiego.cartrackchallenge.worker

import android.content.Context
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.csandiego.cartrackchallenge.data.Credential
import com.github.csandiego.cartrackchallenge.repository.CredentialRepository
import javax.inject.Singleton

@Singleton
class SeedDatabaseWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: CredentialRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = try {
        repository.insert(Credential(0, "username", "password"))
        Result.success()
    } catch (e: Exception) {
        Log.e("SeedDatabaseWorker", "Failed to seed database", e)
        Result.failure()
    }
}