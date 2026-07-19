package com.example.moniretailpda.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moniretailpda.data.entity.PrinterEntity;
import kotlinx.coroutines.flow.Flow

@Dao
interface PrinterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPrinter(printer:PrinterEntity)

    @Query("UPDATE printer_table SET printerName =:printerName, ipAddress=:ipAddress, connectionType=:connectionType WHERE belongUser = :belongUser")
    suspend fun updatePrinter(printerName:String,ipAddress:String,connectionType:String,belongUser:String)

    @Query("SELECT * FROM printer_table where belongUser = :belongUser")
    fun getAllPrinter(belongUser:String): Flow<List<PrinterEntity>>

    @Query("DELETE FROM printer_table")
    suspend fun deletePrinter()
}