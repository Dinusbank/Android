package com.dinusbank.tumbuhin.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiCall {
    private val loggingInceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(loggingInceptor).build()

    val leafesApiService: LeafesApiService by lazy {
        Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            baseUrl("http://34.101.185.208")
            client(client)
        }.build().create(LeafesApiService::class.java)
    }
}