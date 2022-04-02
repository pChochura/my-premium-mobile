package com.pointlessapps.mypremiummobile.http.errors

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.pointlessapps.mypremiummobile.http.errors.mapper.toException
import com.pointlessapps.mypremiummobile.http.errors.model.Error
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Intercepts an API call to check if the response was successful and throws an appropriate exception if not
 */
internal class ErrorHandlingCall(
    private val delegate: Call<Any>,
    private val gson: Gson,
) : Call<Any> by delegate {

    override fun enqueue(callback: Callback<Any>) =
        delegate.enqueue(
            object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        callback.onResponse(call, response)
                    } else {
                        try {
                            val error = gson.fromJson<Error?>(response.errorBody()?.string())
                            callback.onFailure(call, error.toException(response.code()))
                        } catch (_: JsonSyntaxException) {
                            callback.onResponse(call, response)
                        } catch (_: IllegalArgumentException) {
                            callback.onResponse(call, response)
                        }
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    callback.onFailure(call, t)
                }
            },
        )

    override fun clone(): Call<Any> = ErrorHandlingCall(delegate.clone(), gson)

    private inline fun <reified T> Gson.fromJson(json: String?) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
}
