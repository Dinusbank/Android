//package com.dinusbank.tumbuhin.data.local.room
//
//import androidx.lifecycle.LiveData
//import androidx.room.*
//import com.dinusbank.tumbuhin.data.local.entity.LeafesEntities
//
//@Dao
//interface LeafesDao {
//
//    @Query("SELECT * FROM leafes_entities")
//    fun getLeafes(): LiveData<List<LeafesEntities>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertLeafes(leafesEntities: List<LeafesEntities>)
//
//    @Update
//    fun updateLeafes(leafesEntities: LeafesEntities)
//}