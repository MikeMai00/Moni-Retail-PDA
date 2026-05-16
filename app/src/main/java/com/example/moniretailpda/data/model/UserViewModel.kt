package com.example.moniretailpda.data.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moniretailpda.data.database.PDA_Database
import com.example.moniretailpda.data.entity.StaffEntity
import com.example.moniretailpda.data.entity.UserEntity
import com.example.moniretailpda.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application:Application): AndroidViewModel(application) {

    private val repository: UserRepository

    private val _userentity = MutableLiveData<UserEntity?>()
    val userentity: MutableLiveData<UserEntity?> = _userentity

    private val _userboolean = MutableLiveData<Boolean>()
    val userboolean: MutableLiveData<Boolean> = _userboolean

    init{
        val userDao = PDA_Database.getDatabase(application).userDao()
        repository = UserRepository(userDao)

    }

    fun addUser(email:String, password:String){
        viewModelScope.launch(Dispatchers.IO){
            try{
                val user = UserEntity(email = email, password = password)

                repository.addUser(user)
                _userboolean.postValue(true)
            }
            catch (e:Exception){
                _userboolean.postValue(false)

            }
        }
    }

    fun login(email: String,password:String){
        viewModelScope.launch(Dispatchers.IO){

            val userEntity = repository.login(email,password)
            userentity.postValue(userEntity)
        }
    }
}