package com.example.notesapp.room.todolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodolistDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(todolist: Todolist)

    @Query("SELECT * FROM 'to-do-list' ORDER BY id DESC")
    fun getAll(): Flow<List<Todolist>>
    @Delete
    suspend fun delete(todolist: Todolist)
    @Update
    suspend fun update(todolist: Todolist)
    @Query("SELECT * FROM 'to-do-list' WHERE id = :id")
    suspend fun getById(id: Int): Todolist?

    @Query("DELETE FROM 'to-do-list' WHERE id IN (:taskIds)")
    suspend fun deleteTasksByIds(taskIds: List<Int>)


}