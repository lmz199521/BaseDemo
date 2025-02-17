package com.example.demo.http

import com.lmz.network.http.IHttpRequestService
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitUtils {

    private var baseUrl: String = ""
    private const val CONNECT_TIME = 5 * 60L
    private val map: HashMap<String, IHttpRequestService> = hashMapOf()
    private val retrofit: Retrofit by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME, TimeUnit.SECONDS)
            .readTimeout(CONNECT_TIME, TimeUnit.SECONDS)
            .writeTimeout(CONNECT_TIME, TimeUnit.SECONDS)
            .addInterceptor(HttpHeadInterceptor())
            .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
            .build()

        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    fun init(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    fun <T : IHttpRequestService> create(service: Class<T>): T {
        val requestService = map[service.simpleName] as T?
        if (requestService != null) {
            return requestService
        } else {
            val create = retrofit.create(service)
            map[service.simpleName] = create
            return create
        }
    }

}