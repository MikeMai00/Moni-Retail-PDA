package com.example.moniretailpda.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moniretailpda.data.dao.UserDao
import com.example.moniretailpda.data.dao.ProductDao
import com.example.moniretailpda.data.dao.StaffDao
import com.example.moniretailpda.data.entity.ProductEntity
import com.example.moniretailpda.data.entity.UserEntity
import com.example.moniretailpda.data.entity.StaffEntity


@Database(entities = [UserEntity::class, ProductEntity::class,StaffEntity::class], version = 3, exportSchema = true)
abstract class PDA_Database : RoomDatabase(){

    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun staffDao(): StaffDao

    companion object{
        @Volatile
        private var INSTANCE: PDA_Database? = null
        fun getDatabase(context:Context): PDA_Database{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PDA_Database::class.java,
                    "database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

