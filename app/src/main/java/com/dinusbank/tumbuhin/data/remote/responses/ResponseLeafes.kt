package com.dinusbank.tumbuhin.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ResponseLeafes(
    @field:SerializedName("result")
    val result: ArrayList<ResponseDataLeafes>? = null
)
