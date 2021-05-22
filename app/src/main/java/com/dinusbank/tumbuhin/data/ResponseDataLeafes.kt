package com.dinusbank.tumbuhin.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseDataLeafes(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("nama")
    val name: String? = null,

    @field:SerializedName("nama_latin")
    val latinName: String? = null,

    @field:SerializedName("manfaat")
    val benefits: String? = null,

    @field:SerializedName("komposisi")
    val composition: String? = null
) : Parcelable
