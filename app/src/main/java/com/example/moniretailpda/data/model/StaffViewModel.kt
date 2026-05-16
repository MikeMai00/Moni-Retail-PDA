package com.example.moniretailpda.data.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moniretailpda.data.database.PDA_Database
import com.example.moniretailpda.data.entity.StaffEntity

import com.example.moniretailpda.data.repository.StaffRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StaffViewModel(application: Application): AndroidViewModel(application) {
    private val repository: StaffRepository


    private val _staffentity = MutableLiveData<StaffEntity?>()
    val staffentity: MutableLiveData<StaffEntity?> = _staffentity

    private val _stafflist = MutableLiveData<List<StaffEntity>>()
    val stafflist: MutableLiveData<List<StaffEntity>> = _stafflist


    private val _staffboolean = MutableLiveData<Boolean>()
    val staffboolean: MutableLiveData<Boolean> = _staffboolean

    private val _staffdelete = MutableLiveData<Boolean>()
    val staffdelete: MutableLiveData<Boolean> = _staffdelete

    init {
        val staffDao = PDA_Database.getDatabase(application).staffDao()
        repository = StaffRepository(staffDao)


    }

    fun getStaff(belongUser: String){
        viewModelScope.launch(Dispatchers.IO){
            val staffList = repository.getStaff(belongUser).first()
            _stafflist.postValue(staffList)
        }
    }

    fun addStaff(staffName:String, password:String,belongUser:String){
        viewModelScope.launch(Dispatchers.IO){
            try{
                val staff = StaffEntity(name = staffName, password = password, belongUser = belongUser)

                repository.addStaff(staff)
                _staffboolean.postValue(true)
            }
            catch (e:Exception){
                _staffboolean.postValue(false)

            }
        }
    }

    fun deleteStaff(name: List<String>, belongUser: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                repository.deleteStaff(name)
                _staffdelete.postValue(true)
                getStaff(belongUser)
            }
            catch (e:Exception){
                _staffdelete.postValue(false)
            }
        }
    }

    fun staffLogin(name: String,password:String){
        viewModelScope.launch(Dispatchers.IO){

            val staffEntity = repository.staffLogin(name,password)
            staffentity.postValue(staffEntity)
        }
    }

    fun resetLoginStatus(){
        staffentity.value = null
    }
}
