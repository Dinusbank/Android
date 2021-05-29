package com.dinusbank.tumbuhin.api

import com.dinusbank.tumbuhin.data.remote.responses.ResponseLeafes
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface LeafesApiService {
    @GET("/")
    fun getLeafes(): Call<ResponseLeafes>

    @FormUrlEncoded
    @POST("/search.php")
    fun getSearch(@Field("search") search: String): Call<ResponseLeafes>

}