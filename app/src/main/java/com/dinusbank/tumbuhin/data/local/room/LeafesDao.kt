package com.dinusbank.tumbuhin.data.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.dinusbank.tumbuhin.data.local.entity.LeafesEntities

@Dao
interface LeafesDao {

    @Query("SELECT * FROM leafes_entities ORDER BY leafes_name ASC")
    fun getLeafes(): DataSource.Factory<Int, LeafesEntities>

    @Query("SELECT * FROM leafes_entities WHERE leafes_name LIKE :search OR leafes_benefits LIKE :search ORDER BY leafes_name ASC")
    fun getLeafesSearch(search: String): DataSource.Factory<Int, LeafesEntities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLeafes(leafesEntities: List<LeafesEntities>)
}