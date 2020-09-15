package com.fdev.instagramclone.business.data.util

import com.fdev.instagramclone.business.data.cache.CacheConstants.CACHE_TIMEOUT
import com.fdev.instagramclone.business.data.cache.CacheErrors.CACHE_ERROR_TIMEOUT
import com.fdev.instagramclone.business.data.cache.CacheErrors.CACHE_ERROR_UNKNOWN
import com.fdev.instagramclone.business.data.cache.CacheResult
import com.fdev.instagramclone.business.data.network.NetworkConstants.NETWORK_TIMEOUT
import com.fdev.instagramclone.business.data.network.NetworkErrors.NETWORK_ERROR_TIMEOUT
import com.fdev.instagramclone.business.data.network.NetworkErrors.NETWORK_ERROR_UNKNOWN
import com.fdev.instagramclone.business.data.network.NetworkResult
import com.fdev.instagramclone.business.data.util.GenericErrors.ERROR_UNKNOWN
import com.fdev.instagramclone.util.cLog
import com.fdev.instagramclone.util.printLogD
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException

/**
 * Reference: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 */

suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        spesificErrorHandler: (suspend (throwable : Throwable) -> NetworkResult<Nothing>?)? = null,
        apiCall: suspend () -> T?

): NetworkResult<T?> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            withTimeout(NETWORK_TIMEOUT) {
                NetworkResult.Success(apiCall.invoke())
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            cLog("Api call" + throwable.message)
            when (throwable) {
                is TimeoutCancellationException -> {
                    val code = 408 // timeout error code
                    NetworkResult.GenericError(code, NETWORK_ERROR_TIMEOUT)
                }
                is IOException -> {
                    NetworkResult.NetworkError
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    cLog(errorResponse)
                    NetworkResult.GenericError(
                            code,
                            errorResponse
                    )
                }
                else -> {

                    if(spesificErrorHandler == null){
                        cLog(NETWORK_ERROR_UNKNOWN)
                        NetworkResult.GenericError(
                                null,
                                NETWORK_ERROR_UNKNOWN
                        )
                    }else{
                        val networkSpecificError = spesificErrorHandler.invoke(throwable)

                        if(networkSpecificError== null){
                            NetworkResult.GenericError(
                                    null,
                                    NETWORK_ERROR_UNKNOWN
                            )
                        }else{
                            networkSpecificError
                        }
                    }

                }


            }
        }
    }
}


suspend fun <T> safeCacheCall(
        dispatcher: CoroutineDispatcher,
        cacheCall: suspend () -> T?
): CacheResult<T?> {
    return withContext(dispatcher) {
        try {
            // throws TimeoutCancellationException
            withTimeout(CACHE_TIMEOUT) {
                CacheResult.Success(cacheCall.invoke())
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            cLog("CACHE CALL :" + throwable.message)
            when (throwable) {

                is TimeoutCancellationException -> {
                    CacheResult.GenericError(CACHE_ERROR_TIMEOUT)
                }
                else -> {
                    cLog(CACHE_ERROR_UNKNOWN)
                    CacheResult.GenericError(CACHE_ERROR_UNKNOWN)
                }
            }
        }
    }
}


private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.string()
    } catch (exception: Exception) {
        ERROR_UNKNOWN
    }
}