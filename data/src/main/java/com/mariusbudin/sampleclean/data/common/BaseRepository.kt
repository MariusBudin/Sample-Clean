package com.mariusbudin.sampleclean.data.common

import android.content.Context
import com.mariusbudin.sampleclean.data.common.platform.NetworkHandler
import com.mariusbudin.sampleclean.core.exception.Failure
import com.mariusbudin.sampleclean.core.functional.Either
import retrofit2.Call

class BaseRepository {

    open class Remote(private val networkHandler: NetworkHandler) {

        protected fun <T, R> request(
            call: Call<T>,
            transform: (T) -> R,
            defaultValue: R
        ): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(response.body()?.let(transform) ?: defaultValue)
                    false -> Either.Left(Failure.ServerError)
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError)
            }
        }
    }

    open class Local(private val context: Context)
}
