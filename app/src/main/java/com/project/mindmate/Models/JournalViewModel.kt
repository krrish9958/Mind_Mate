package com.project.mindmate.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mindmate.Database.JournalDatabase
import com.example.mindmate.Database.JournalsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JournalViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : JournalsRepository

    val allJournals : LiveData<List<Journal>>

    init {
        val dao = JournalDatabase.getDatabase(application).getJournalDao()
        repository = JournalsRepository(dao)
        allJournals = repository.allJournals
    }

    fun deleteJournal (journal: Journal) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(journal)
    }

    fun insertJournal (journal: Journal) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(journal)
    }

    fun updateJournal (journal: Journal) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(journal)
    }

}