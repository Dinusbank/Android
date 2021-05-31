package com.dinusbank.tumbuhin.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ResponseDataLeafes(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("nama")
    val name: String? = null,

    @field:SerializedName("nama_latin")
    val latinName: String? = null,

    @field:SerializedName("manfaat")
    val benefits: String? = null,

    @field:SerializedName("kandungan")
    val composition: String? = null,

    @field:SerializedName("gambar")
    val imageLeafes: String? = null
)
