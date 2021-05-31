package com.dinusbank.tumbuhin.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dinusbank.tumbuhin.data.local.entity.LeafesEntities
import com.dinusbank.tumbuhin.data.local.sources.LocalDataSource
import com.dinusbank.tumbuhin.data.remote.ApiResponse
import com.dinusbank.tumbuhin.data.remote.responses.ResponseDataLeafes
import com.dinusbank.tumbuhin.data.remote.sources.RemoteDataSource
import com.dinusbank.tumbuhin.utils.AppExecutors
import com.dinusbank.tumbuhin.vo.Resource

class LeafesRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors): LeafesDataSource{

    companion object{
        @Volatile
        private var instance: LeafesRepository? = null

        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource, appExecutors: AppExecutors): LeafesRepository =
            instance ?: synchronized(this) {
                instance ?: LeafesRepository(remoteData, localData, appExecutors).apply {
                    instance = this
                }
            }
    }

    override fun getLeafes(): LiveData<Resource<PagedList<LeafesEntities>>> {
        return object : NetworkBoundResource<PagedList<LeafesEntities>, List<ResponseDataLeafes>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<LeafesEntities>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getLeafes(), config).build()
            }

            override fun shouldFetch(data: PagedList<LeafesEntities>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<ResponseDataLeafes>>> {
                return remoteDataSource.getLeafes()
            }

            override fun saveCallResult(data: List<ResponseDataLeafes>) {
                val leafesList = ArrayList<LeafesEntities>()
                for (response in data){
                    val leafesEntities = LeafesEntities(
                        response.id,
                        response.name,
                        response.latinName,
                        response.benefits,
                        response.composition,
                        response.imageLeafes
                    )
                    leafesList.add(leafesEntities)
                }
                localDataSource.insertLeafes(leafesList)
            }
        }.asLiveData()
    }

    override fun getLeafesSearch(search: String): LiveData<PagedList<LeafesEntities>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getLeafesSearch(search), config).build()
    }
}