@file:Suppress("UNCHECKED_CAST")

package com.dinusbank.tumbuhin.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dinusbank.tumbuhin.data.LeafesRepository
import com.dinusbank.tumbuhin.di.Injection

class ViewModelFactory(private val leafesRepository: LeafesRepository): ViewModelProvider.NewInstanceFactory() {

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply { instance = this }
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(leafesRepository) as T
            }
            else -> throw Throwable("Unkwon ViewModel Class: " + modelClass.name)
        }
    }
}