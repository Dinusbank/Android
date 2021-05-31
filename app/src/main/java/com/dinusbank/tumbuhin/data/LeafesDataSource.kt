package com.dinusbank.tumbuhin.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dinusbank.tumbuhin.data.local.entity.LeafesEntities
import com.dinusbank.tumbuhin.vo.Resource

interface LeafesDataSource {
    fun getLeafes(): LiveData<Resource<PagedList<LeafesEntities>>>

    fun getLeafesSearch(search: String): LiveData<PagedList<LeafesEntities>>
}