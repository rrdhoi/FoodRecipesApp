package com.jagoteori.foodrecipesapp.data.source.remote

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resumeWithException

inline fun <T> safeCall(action: () -> ApiResponse<T>): ApiResponse<T> {
    return try {
        action()
    } catch (e: Exception) {
        ApiResponse.Error(e.message ?: "An unknown Error Occurred")
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T? {
    // fast path
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException(
                    "Task $this was cancelled normally."
                )
            } else {
                result
            }
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                if (isCanceled) cont.cancel() else cont.resume(result, onCancellation = null)
            } else {
                cont.resumeWithException(e)
            }
        }
    }
}