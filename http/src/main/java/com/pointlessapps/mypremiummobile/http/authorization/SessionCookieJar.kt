package com.pointlessapps.mypremiummobile.http.authorization

import android.content.SharedPreferences
import com.google.gson.Gson
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

internal class SessionCookieJar(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences,
) : CookieJar {

    private val cookies = mutableMapOf<String, Cookie?>()

    init {
        sharedPreferences.getStringSet(COOKIES_KEY, null)?.also { cookies ->
            val transformedCookies = cookies.map { gson.fromJson(it, Cookie::class.java) }
            this.cookies.putAll(transformedCookies.associateBy { it.name })
        }
    }

    override fun loadForRequest(url: HttpUrl) = cookies.values.toList().filterNotNull()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        this.cookies.putAll(cookies.associateBy { it.name })
        sharedPreferences.edit().apply {
            putStringSet(COOKIES_KEY, cookies.map { gson.toJson(it) }.toSet())
        }.apply()
    }

    companion object {
        const val SHARED_PREFERENCES_KEY = "cookies_store"

        private const val COOKIES_KEY = "cookies"
    }
}
