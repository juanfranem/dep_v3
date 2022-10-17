@file:OptIn(DelicateCoroutinesApi::class)

package com.virgendelosdoloreslacarlota.dep.base

import kotlinx.coroutines.*

abstract class BaseUseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params, scope: CoroutineScope): Type

    operator fun invoke(
        params: Params,
        scope: CoroutineScope = GlobalScope,
        onResult: (Result<Type>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                run(params, scope)
            }
            try {
                val result = deferred.await()
                onResult(Result.success(result))
            } catch (e: Exception) {
                onResult(Result.failure(e))
            }
        }
    }

    class None
}