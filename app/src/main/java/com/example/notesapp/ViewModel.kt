package com.example.notesapp

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.notesapp.room.note.AppDatabase
import com.example.notesapp.room.note.Note
import com.example.notesapp.room.note.NoteRepository
import com.example.notesapp.room.todolist.Todolist
import com.example.notesapp.room.todolist.TodolistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ViewModel(app: Application) : AndroidViewModel(app) {
    val title = MutableStateFlow("")
    val content = MutableStateFlow("")
    val date = System.currentTimeMillis()
    val deadline = MutableStateFlow<Long?>(null)
    val todolistTitle = MutableStateFlow("")
    val isClicked = mutableStateMapOf<Int, Boolean>()

    private val noteRepository: NoteRepository
    val notes: Flow<List<Note>>

    private val todolistRepository: TodolistRepository

    private val _todolists = MutableStateFlow<List<Todolist>>(emptyList())
    val todolists: StateFlow<List<Todolist>> = _todolists

    var editNoteId: Int? = null
    val editingTaskId = mutableStateOf<Int?>(null)
    val editingTaskTitle = mutableStateOf("")


    init {
        val db = Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "notes.db"
        )
            .build()

        noteRepository = NoteRepository(db.noteDao())
        todolistRepository = TodolistRepository(db.todolistDao())

        notes = noteRepository.getAll()
        viewModelScope.launch {
            todolistRepository.getAll().collect {
                _todolists.value = it
            }
        }
    }


    fun loadNote(noteId: Int) {
        viewModelScope.launch {
            val note = noteRepository.getById(noteId)
            title.value = note?.title ?: ""
            content.value = note?.content ?: ""
            editNoteId = note?.id
        }
    }


    fun saveNote() {
        viewModelScope.launch {
            if (editNoteId != null) {
                noteRepository.update(
                    Note(
                        id = editNoteId!!,
                        title = title.value,
                        content = content.value,
                        date = date
                    )
                )
            } else {
                noteRepository.save(
                    Note(
                        title = title.value,
                        content = content.value,
                        date = date
                    )
                )
            }
            title.value = ""
            content.value = ""
            editNoteId = null
        }
    }

    fun deleteNote() {
        editNoteId?.let { id ->
            viewModelScope.launch {
                noteRepository.delete(
                    Note(
                        id = id,
                        title = title.value,
                        content = content.value,
                        date = date
                    )
                )
                editNoteId = null
                title.value = ""
                content.value = ""
            }
        }
    }


    fun saveTodolist(title: String, deadline: Long?) {
        val newTask = Todolist(title = title, deadline = deadline)
        viewModelScope.launch {
            todolistRepository.save(newTask)
            _todolists.value = todolistRepository.getAll().first()
        }
    }


    fun updateTaskTitle(taskId: Int, newTitle: String) {
        val updated = _todolists.value.map { task ->
            if (task.id == taskId) task.copy(title = newTitle) else task
        }
        _todolists.value = updated
    }
    fun updateTodolist(taskId: Int, newTitle: String, newDeadline: Long?) {
        viewModelScope.launch {
            val task = todolistRepository.getById(taskId)
            if (task != null) {
                val updatedTask = task.copy(title = newTitle, deadline = newDeadline)
                todolistRepository.update(updatedTask)
                _todolists.value = todolistRepository.getAll().first()
            }
        }
    }
    fun deleteTasks(taskIds: List<Int>) {
        viewModelScope.launch {
            todolistRepository.deleteTaskById(taskIds)
        }
    }
}
