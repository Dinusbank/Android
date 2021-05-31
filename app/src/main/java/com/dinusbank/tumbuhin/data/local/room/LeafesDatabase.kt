package com.dinusbank.tumbuhin.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dinusbank.tumbuhin.data.local.entity.LeafesEntities

@Database(entities = [LeafesEntities::class], version = 1, exportSchema = false)
abstract class LeafesDatabase: RoomDatabase() {
    abstract fun leafesDao(): LeafesDao

    companion object{
        @Volatile
        private var INSTANCE: LeafesDatabase? = null

        fun getInstance(context: Context): LeafesDatabase =
            INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    LeafesDatabase::class.java,
                    "leafes.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}