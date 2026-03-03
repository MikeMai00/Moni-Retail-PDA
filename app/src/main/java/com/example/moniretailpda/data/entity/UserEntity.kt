package com.example.moniretailpda.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


//用于用户的实体类
@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String = "",
    val password: String = "",

    ) {

}