package com.pointlessapps.mypremiummobile.http.authorization

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.pointlessapps.mypremiummobile.datasource.auth.AuthorizationTokenStore
import com.pointlessapps.mypremiummobile.http.errors.model.Error
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Invocation

internal class AuthorizationInterceptor(
    private val authorizationTokenStore: AuthorizationTokenStore,
    private val gson: Gson,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        request
            .tag(Invocation::class.java)
            ?.method()
            ?.getAnnotation(Authorize::class.java)
            ?: return chain.proceed(request)

        return try {
            var response = chain.proceed(
                request.buildAuthorizedRequest(
                    authorizationTokenStore.getAuthToken(),
                ),
            )

            if (response.code == HTTP_CODE_UNAUTHORIZED) {
                authorizationTokenStore.refreshToken()

                response = chain.proceed(
                    request.buildAuthorizedRequest(
                        authorizationTokenStore.getAuthToken(),
                    ),
                )
            }
            response
        } catch (e: Exception) {

            val responseBody = try {
                val error = Error(
                    name = e.javaClass.simpleName,
                    message = e.localizedMessage,
                )
                gson.toJson(error)
            } catch (_: IllegalArgumentException) {
                e.toString()
            } catch (_: JsonIOException) {
                e.toString()
            }.toResponseBody()

            Response
                .Builder()
                .request(request)
                .protocol(chain.connection()?.protocol() ?: Protocol.HTTP_1_1)
                .message(e.message.toString())
                .code(HTTP_CODE_UNAUTHORIZED)
                .body(responseBody)
                .build()
        }
    }

    private fun Request.buildAuthorizedRequest(authToken: String): Request =
        newBuilder()
            .addHeader(AUTHORIZATION_HEADER, "$AUTHORIZATION_BEARER $authToken")
            .build()

    private companion object {
        private const val HTTP_CODE_UNAUTHORIZED = 401
    }
}
