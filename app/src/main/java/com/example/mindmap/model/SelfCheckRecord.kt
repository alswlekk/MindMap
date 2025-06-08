package com.example.mindmap.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "self_check_records")
data class SelfCheckRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val score: Int,
    val memo: String
)