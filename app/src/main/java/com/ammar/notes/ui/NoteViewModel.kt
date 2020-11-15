package com.ammar.notes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ammar.notes.db.NoteModel
import com.ammar.notes.db.repositories.NoteRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    //step 5
    fun insert(note: NoteModel) =
        viewModelScope.launch {
            repository.insert(note)
        }
    fun update(note: NoteModel) =
        viewModelScope.launch {
            repository.update(note)
        }
    fun delete(note: NoteModel) = viewModelScope.launch {
        repository.delete(note)
    }

    fun  clearAll()=viewModelScope.launch {
        repository.deleteAll()
    }
    //read operation so it do not need  to be executed in a coroutine
    fun getAllNotes() = repository.getAllNotes()
}