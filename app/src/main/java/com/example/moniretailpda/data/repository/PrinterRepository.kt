package com.example.moniretailpda.data.repository

import com.example.moniretailpda.data.dao.PrinterDao
import com.example.moniretailpda.data.entity.PrinterEntity
import kotlinx.coroutines.flow.Flow

class PrinterRepository(private val printerDao: PrinterDao) {

    suspend fun addPrinter(printer: PrinterEntity){
        printerDao.addPrinter(printer)
    }

    suspend fun updatePrinter(printerName:String,ipAddress:String,connectionType:String,belongUser:String){
        printerDao.updatePrinter(printerName,ipAddress,connectionType,belongUser)
    }

    suspend fun getAllPrinter(belongUser:String) : Flow<List<PrinterEntity>> {
        return printerDao.getAllPrinter(belongUser)
    }

    suspend fun deletePrinter(){
        printerDao.deletePrinter()
    }

}


