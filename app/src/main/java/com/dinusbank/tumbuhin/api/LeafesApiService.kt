package com.dinusbank.tumbuhin.api

import com.dinusbank.tumbuhin.data.ResponseDataLeafes
import com.dinusbank.tumbuhin.data.ResponseLeafes
import retrofit2.Call
import retrofit2.http.GET

interface LeafesApiService {
    @GET("/")
    fun getLeafes(): Call<ResponseLeafes>

    fun getDetailLeafes(): Call<ResponseDataLeafes>
}