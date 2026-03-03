package com.example.moniretailpda.data.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moniretailpda.data.database.PDA_Database
import com.example.moniretailpda.data.entity.UserEntity
import com.example.moniretailpda.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application:Application): AndroidViewModel(application) {

    private val repository: UserRepository

    init{
        val userDao = PDA_Database.getDatabase(application).userDao()
        repository = UserRepository(userDao)

    }

    fun addUser(user:UserEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }
}