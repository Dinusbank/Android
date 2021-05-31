package com.dinusbank.tumbuhin.data.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "leafes_entities")
data class LeafesEntities(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "leafes_id")
    var id: Int? = null,

    @ColumnInfo(name = "leafes_name")
    var name: String? = null,

    @ColumnInfo(name = "leafes_latinName")
    var latinName: String? = null,

    @ColumnInfo(name = "leafes_benefits")
    var benefits: String? = null,

    @ColumnInfo(name = "leafes_composition")
    var composition: String? = null,

    @ColumnInfo(name = "leafes_images")
    var imageLeafes: String? = null
): Parcelable