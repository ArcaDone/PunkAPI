package com.arcadone.cheers.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.arcadone.cheers.CheersApp
import com.arcadone.cheers.model.BeerItem
import com.arcadone.cheers.util.DATA_FILENAME
import com.arcadone.cheers.database.BeerDatabase
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import okio.Okio
import timber.log.Timber

class DatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    // No need to cancel this scope as it'll be torn down with the process
    // val applicationScope = CoroutineScope(SupervisorJob())

    override suspend fun doWork(): Result = coroutineScope {
        withContext(Dispatchers.IO) {
            try {
                @Suppress("BlockingMethodInNonBlockingContext")
                applicationContext.assets.open(DATA_FILENAME).use { inputStream ->
                    JsonReader.of(Okio.buffer(Okio.source(inputStream))).use { jsonReader ->
                        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                        val listType =
                            Types.newParameterizedType(List::class.java, BeerItem::class.java)
                        val adapter: JsonAdapter<List<BeerItem>> = moshi.adapter(listType)
                        val list = adapter.fromJson(jsonReader)

                        BeerDatabase.getInstance(
                            applicationContext,
                            CheersApp.instance.applicationScope
                        ).beerDao().insertBeers(list!!)
                        Result.success()
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error seeding database")
                Result.failure()
            }
        }
    }
}
