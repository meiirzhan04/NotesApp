package com.example.notesapp.room.note

class NoteRepository(private val dao: NoteDao) {
    suspend fun save(note: Note) = dao.insert(note)
    fun getAll() = dao.getAll()
    suspend fun delete(note: Note) = dao.delete(note)
    suspend fun update(note: Note) = dao.update(note)
    suspend fun getById(noteId: Int) = dao.getById(noteId)

}