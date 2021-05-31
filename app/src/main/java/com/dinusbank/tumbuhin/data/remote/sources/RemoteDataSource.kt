package com.dinusbank.tumbuhin.data.remote.sources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dinusbank.tumbuhin.api.ApiCall
import com.dinusbank.tumbuhin.data.remote.ApiResponse
import com.dinusbank.tumbuhin.data.remote.responses.ResponseDataLeafes
import com.dinusbank.tumbuhin.data.remote.responses.ResponseLeafes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    fun getLeafes(): LiveData<ApiResponse<List<ResponseDataLeafes>>> {
        val response = ApiCall.leafesApiService.getLeafes()
        val resultLeafes = MutableLiveData<ApiResponse<List<ResponseDataLeafes>>>()

        response.enqueue(object : Callback<ResponseLeafes>{
            override fun onResponse(call: Call<ResponseLeafes>, response: Response<ResponseLeafes>) {
                resultLeafes.value = ApiResponse.success(response.body()?.result as List<ResponseDataLeafes>)
            }

            override fun onFailure(call: Call<ResponseLeafes>, t: Throwable) {
                Log.e("ResponseLeafes", "Data is Not Available")
            }

        })
        return resultLeafes
    }

    fun getLeafesSearch(search: String): LiveData<ApiResponse<List<ResponseDataLeafes>>> {
        val response = ApiCall.leafesApiService.getSearch(search)
        val resultLeafes = MutableLiveData<ApiResponse<List<ResponseDataLeafes>>>()

        response.enqueue(object : Callback<ResponseLeafes>{
            override fun onResponse(call: Call<ResponseLeafes>, response: Response<ResponseLeafes>) {
                resultLeafes.value = ApiResponse.success(response.body()?.result as List<ResponseDataLeafes>)
            }

            override fun onFailure(call: Call<ResponseLeafes>, t: Throwable) {
                Log.e("ResponseLeafes", "Data is Not Available")
            }
        })
        return resultLeafes
    }
}