package com.dinusbank.tumbuhin.data.local.sources

import androidx.paging.DataSource
import com.dinusbank.tumbuhin.data.local.entity.LeafesEntities
import com.dinusbank.tumbuhin.data.local.room.LeafesDao

class LocalDataSource private constructor(private val leafesDao: LeafesDao){

    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(leafesDao: LeafesDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(leafesDao)
    }

    fun getLeafes(): DataSource.Factory<Int, LeafesEntities> = leafesDao.getLeafes()

    fun getLeafesSearch(search: String): DataSource.Factory<Int, LeafesEntities> = leafesDao.getLeafesSearch(search)

    fun insertLeafes(leafesEntities: List<LeafesEntities>) = leafesDao.insertLeafes(leafesEntities)

}