package com.pointlessapps.mypremiummobile.http.authorization

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

internal class SessionCookieJar : CookieJar {

    private val cookies = mutableMapOf<String, Cookie?>()

    override fun loadForRequest(url: HttpUrl) = cookies.values.toList().filterNotNull()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        this.cookies.putAll(cookies.associateBy { it.name })
    }
}
