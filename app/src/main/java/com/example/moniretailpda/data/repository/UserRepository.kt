package com.example.moniretailpda.data.repository

import com.example.moniretailpda.data.dao.UserDao
import com.example.moniretailpda.data.entity.UserEntity

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(user: UserEntity){
        userDao.addUser(user)
    }
    suspend fun login(email:String,password:String){
        userDao.login(email,password)
    }

}