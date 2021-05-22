package com.dinusbank.tumbuhin.data

import com.google.gson.annotations.SerializedName

data class ResponseLeafes(
    @field:SerializedName("result")
    val result: ArrayList<ResponseDataLeafes>? = null
)
