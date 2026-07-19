package com.example.moniretailpda.data.repository

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

    suspend fun getStaff(belongUser:String) : Flow<List<StaffEntity>> {
        return staffDao.getStaff(belongUser)
    }

    suspend fun deleteStaff(name: List<String>){
        staffDao.deleteStaff(name)
    }

    suspend fun staffLogin(name:String,password:String) : StaffEntity?{
        return staffDao.staffLogin(name,password)
    }
}