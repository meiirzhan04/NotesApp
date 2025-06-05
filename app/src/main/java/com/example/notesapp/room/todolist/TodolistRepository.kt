package com.example.notesapp.room.todolist

class TodolistRepository(private val dao: TodolistDao) {
    suspend fun save(todolist: Todolist) = dao.insert(todolist)
    fun getAll() = dao.getAll()
    suspend fun delete(todolist: Todolist) = dao.delete(todolist)
    suspend fun update(todolist: Todolist) = dao.update(todolist)
    suspend fun getById(id: Int) = dao.getById(id)
    suspend fun deleteTaskById(taskIds: List<Int>) = dao.deleteTasksByIds(taskIds)

}