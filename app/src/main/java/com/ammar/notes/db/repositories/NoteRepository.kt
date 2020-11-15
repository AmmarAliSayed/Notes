package com.ammar.notes.db.repositories

import com.ammar.notes.db.NoteDatabase
import com.ammar.notes.db.NoteModel
//step 4
class NoteRepository(  private val db: NoteDatabase) {
    suspend fun insert(note: NoteModel) = db.getNoteDao().insert(note)
    suspend fun update(note: NoteModel) = db.getNoteDao().update(note)
    suspend fun delete(note: NoteModel) = db.getNoteDao().delete(note)
    suspend fun  deleteAll(){
        db.getNoteDao().deleteAll()
    }
    fun getAllNotes() = db.getNoteDao().getAllNotes()
}