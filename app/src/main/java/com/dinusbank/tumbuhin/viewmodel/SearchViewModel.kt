package com.dinusbank.tumbuhin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dinusbank.tumbuhin.data.LeafesRepository
import com.dinusbank.tumbuhin.data.local.entity.LeafesEntities
import com.dinusbank.tumbuhin.vo.Resource

class SearchViewModel(private val leafesRepository: LeafesRepository): ViewModel() {

    fun getLeafes(): LiveData<Resource<PagedList<LeafesEntities>>> = leafesRepository.getLeafes()

    fun getSearch(search: String): LiveData<PagedList<LeafesEntities>> = leafesRepository.getLeafesSearch(search)
}