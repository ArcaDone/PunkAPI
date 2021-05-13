package com.arcadone.cheers.repository

import kotlinx.coroutines.Dispatchers
import com.arcadone.cheers.model.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.SUCCESS] - with data from database
 * [Result.ERROR] - if error has occurred from any source
 * [Result.LOADING]
 */

fun <T, A> loadBeers(
    loadFromDb: suspend () -> T,
    createCall: suspend () -> Result<A>,
    saveCallResult: suspend (A) -> Unit
): Flow<Result<T>> {
    return flow {
        emit(Result.Loading<T>())
        emit(Result.Success(loadFromDb.invoke()))

        val response = createCall.invoke()
        if (response is Result.Success) {
            saveCallResult(response.data!!)
        } else if (response is Result.Error) {
            emit(Result.Error<T>(response.message!!))
            delay(1_000)
        }
        emit(Result.Success(loadFromDb.invoke()))
    }
        .flowOn(Dispatchers.IO)
        .catch { Result.Error<T>("Error") }
}