package com.dinusbank.tumbuhin.di

import android.content.Context
import com.dinusbank.tumbuhin.data.LeafesRepository
import com.dinusbank.tumbuhin.data.local.room.LeafesDatabase
import com.dinusbank.tumbuhin.data.local.sources.LocalDataSource
import com.dinusbank.tumbuhin.data.remote.sources.RemoteDataSource
import com.dinusbank.tumbuhin.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): LeafesRepository {
        val database = LeafesDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource()
        val localDataSource = LocalDataSource.getInstance(database.leafesDao())
        val appExecutors = AppExecutors()

        return LeafesRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}