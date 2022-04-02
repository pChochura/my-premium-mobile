package com.pointlessapps.mypremiummobile.http.errors

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Factory for an [ErrorCallAdapter] to support intercepting calls to the API
 */
internal class ErrorCallAdapterFactory(
    private val gson: Gson,
) : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java ||
            returnType !is ParameterizedType ||
            returnType.actualTypeArguments.size != 1
        ) {
            return null
        }
        @Suppress("UNCHECKED_CAST")
        val nextCallAdapter =
            retrofit.nextCallAdapter(this, returnType, annotations) as CallAdapter<Any, Call<*>>
        return ErrorCallAdapter(
            delegate = nextCallAdapter,
            gson = gson,
        )
    }
}
