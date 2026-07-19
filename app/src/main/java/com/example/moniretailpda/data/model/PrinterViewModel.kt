package com.example.moniretailpda.data.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moniretailpda.data.database.PDA_Database
import com.example.moniretailpda.data.entity.PrinterEntity
import com.example.moniretailpda.data.repository.PrinterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PrinterViewModel(application:Application): AndroidViewModel(application) {

    private val repository: PrinterRepository

    private val _printerentity = MutableLiveData<PrinterEntity?>()
    val printerentity: MutableLiveData<PrinterEntity?> = _printerentity

    private val _printerboolean = MutableLiveData<Boolean>()
    val printerboolean: MutableLiveData<Boolean> = _printerboolean

    private val _printerlist = MutableLiveData<List<PrinterEntity>>()
    val printerlist: MutableLiveData<List<PrinterEntity>> = _printerlist

    private val _printerupdate = MutableLiveData<Boolean>()
    val printerupdate: MutableLiveData<Boolean> = _printerupdate

    init {
        val printerDao = PDA_Database.getDatabase(application).printerDao()
        repository = PrinterRepository(printerDao)

    }

    fun addPrinter(printerName: String, ipAddress: String, connectionType: String, belongUser: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val printer = PrinterEntity(
                    printerName = printerName,
                    ipAddress = ipAddress,
                    connectionType = connectionType,
                    belongUser = belongUser
                )

                repository.addPrinter(printer)
                _printerboolean.postValue(true)
            } catch (e: Exception) {
                _printerboolean.postValue(false)

            }
        }
    }

    fun getAllPrinter(belongUser: String){
        viewModelScope.launch(Dispatchers.IO){
            val printerList = repository.getAllPrinter(belongUser).first()
            _printerlist.postValue(printerList)
        }
    }

    fun updatePrinter(printerName: String, ipAddress: String, connectionType: String, belongUser: String){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                repository.updatePrinter(printerName,ipAddress,connectionType,belongUser)
                _printerupdate.postValue(true)
            }
            catch (e:Exception){
                _printerupdate.postValue(false)
            }
        }
    }

    fun deletePrinter() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deletePrinter()
                _printerboolean.postValue(true)
            }
            catch (e:Exception){
                _printerboolean.postValue(false)

            }
        }

    }
}