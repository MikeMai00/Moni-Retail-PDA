package com.example.moniretailpda.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moniretailpda.data.entity.StaffEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StaffDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStaff(staff: StaffEntity)

    @Query("SELECT * FROM staff_table WHERE password = :password")
    suspend fun getStaffByPassword(password: String): StaffEntity?

    @Query("SELECT * FROM staff_table where belongUser = :belongUser")
    fun getStaff(belongUser:String): Flow<List<StaffEntity>>

    @Query("DELETE FROM staff_table WHERE name IN (:name)")
    suspend fun deleteStaff(name:List<String>)

    @Query("SELECT * FROM staff_table WHERE name = :name AND password = :password LIMIT 1")
    suspend fun staffLogin(name:String, password:String): StaffEntity?
}