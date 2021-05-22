package com.dinusbank.tumbuhin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dinusbank.tumbuhin.api.ApiCall
import com.dinusbank.tumbuhin.data.ResponseDataLeafes
import com.dinusbank.tumbuhin.data.ResponseLeafes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {

    val listLeafesMutable = MutableLiveData<ArrayList<ResponseDataLeafes>>()

    fun getLeafes(){
        val response = ApiCall.leafesApiService.getLeafes()
        response.enqueue(object : Callback<ResponseLeafes>{
            override fun onResponse(call: Call<ResponseLeafes>, response: Response<ResponseLeafes>) {
                val listDataLeafes = response.body()?.result as ArrayList<ResponseDataLeafes>
                listLeafesMutable.postValue(listDataLeafes)
            }

            override fun onFailure(call: Call<ResponseLeafes>, t: Throwable) {
                Log.e("SearchViewModel", "OnFailure: ${t.message.toString()}")
            }

        })
    }

    fun getSearch(search: String){
        val response = ApiCall.leafesApiService.getSearch(search)
        response.enqueue(object : Callback<ResponseLeafes>{
            override fun onResponse(call: Call<ResponseLeafes>, response: Response<ResponseLeafes>) {
                val listDataLeafes = response.body()?.result

                listLeafesMutable.postValue(listDataLeafes)
            }

            override fun onFailure(call: Call<ResponseLeafes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getSearchViewModel(): LiveData<ArrayList<ResponseDataLeafes>>{
        return listLeafesMutable
    }
}