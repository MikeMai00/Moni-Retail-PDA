package com.example.moniretailpda.data.repository

import androidx.lifecycle.LiveData
import com.example.moniretailpda.data.dao.StaffDao
import com.example.moniretailpda.data.entity.StaffEntity
import kotlinx.coroutines.flow.Flow

class StaffRepository(private val staffDao: StaffDao) {
    suspend fun addStaff(staff: StaffEntity){
        staffDao.addStaff(staff)
    }

    suspend fun getStaffByPassword(password:String) : StaffEntity?{
        return staffDao.getStaffByPassword(password)
    }

    suspend fun getAllStaff() : Flow<List<StaffEntity>> {
        return staffDao.getAllStaff()
    }

    suspend fun deleteStaff(name: List<String>){
        staffDao.deleteStaff(name)
    }
}