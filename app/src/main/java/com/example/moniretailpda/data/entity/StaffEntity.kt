package com.example.moniretailpda.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "staff_table")
data class StaffEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val password: String = "",
    val belongUser: String = "",
    ) {

}