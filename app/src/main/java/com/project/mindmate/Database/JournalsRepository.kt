package com.example.mindmate.Database

import androidx.lifecycle.LiveData
import com.project.mindmate.Models.Journal

class JournalsRepository(private val journalDao: JournalDao) {

    val allJournals : LiveData<List<Journal>> = journalDao.getAllJournals()

    suspend fun insert(journal: Journal){
        journalDao.insert(journal)
    }

    suspend fun delete(journal: Journal){
        journalDao.delete(journal)
    }

    suspend fun update(journal: Journal){
        journalDao.update(journal.id, journal.title, journal.journal)
    }
}