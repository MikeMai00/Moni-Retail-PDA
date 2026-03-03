package com.example.moniretailpda.data.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moniretailpda.data.database.PDA_Database
import com.example.moniretailpda.data.entity.StaffEntity
import com.example.moniretailpda.data.repository.StaffRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StaffViewModel(application: Application): AndroidViewModel(application) {
    private val repository: StaffRepository

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

    fun getAllStaff(){
        viewModelScope.launch(Dispatchers.IO){
            val staffList = repository.getAllStaff().first()
            _stafflist.postValue(staffList)
        }
    }

    fun addStaff(staffName:String, password:String){
        viewModelScope.launch(Dispatchers.IO){
            try{
                val staff = StaffEntity(name = staffName, password = password)

                repository.addStaff(staff)
                _staffboolean.postValue(true)
            }
            catch (e:Exception){
                _staffboolean.postValue(false)

            }
        }
    }

    fun deleteStaff(name:List<String>){
        viewModelScope.launch(Dispatchers.IO){
            try {
                repository.deleteStaff(name)
                _staffdelete.postValue(true)
                getAllStaff()
            }
            catch (e:Exception){
                _staffdelete.postValue(false)
            }
        }
    }
}
