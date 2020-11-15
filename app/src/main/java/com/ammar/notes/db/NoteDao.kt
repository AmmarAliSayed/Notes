package com.ammar.notes.db

import androidx.lifecycle.LiveData
import androidx.room.*

//step 2
@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: NoteModel): Long

    @Update
    suspend fun update(note: NoteModel)

    @Delete
    suspend fun delete(note: NoteModel)

    //delete all rows
    //Room provides compile-time checks of SQLite statements.
    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table")
    fun getAllNotes(): LiveData<List<NoteModel>>
}