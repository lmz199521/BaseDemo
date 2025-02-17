package com.example.demo.http

import okhttp3.Interceptor
import okhttp3.Response

class HttpHeadInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newBuilder = chain.request().newBuilder()
        newBuilder.addHeader("", "")
        return chain.proceed(newBuilder.build());
    }
}