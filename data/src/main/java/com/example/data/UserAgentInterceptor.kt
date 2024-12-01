package com.example.data

import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor(
    private val appName: String,
    private val contactInfo: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithUserAgent = originalRequest.newBuilder()
            .header("User-Agent", "$appName ($contactInfo)")
            .build()
        return chain.proceed(requestWithUserAgent)
    }
}