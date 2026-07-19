package com.example.moniretailpda.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.example.moniretailpda.data.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user:UserEntity)


    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email:String, password:String): UserEntity?
}