package com.example.notesapp.room.note

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.room.todolist.Todolist
import com.example.notesapp.room.todolist.TodolistDao

@Database(entities = [Note::class, Todolist::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun todolistDao(): TodolistDao
}