package com.pointlessapps.mypremiummobile.http.errors

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.CallAdapter

internal class ErrorCallAdapter(
    private val delegate: CallAdapter<Any, Call<*>>,
    private val gson: Gson,
) : CallAdapter<Any, Call<*>> by delegate {

    override fun adapt(call: Call<Any>): Call<*> =
        delegate.adapt(ErrorHandlingCall(call, gson))
}
