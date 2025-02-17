package com.lmz.network.http

import retrofit2.http.GET
import retrofit2.http.Path

interface IHttpRequestService {

    @GET("xxx/xxx/{id}")
    suspend fun getArticleById(@Path("id") id: Long): ResponseBean<Boolean>

}