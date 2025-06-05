package com.example.notesapp.room.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "to-do-list")
data class Todolist(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val deadline: Long? = null
)