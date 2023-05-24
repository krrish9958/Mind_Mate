package com.example.mindmate.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.mindmate.Models.Journal


@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(journal: Journal)

    @Delete
    suspend fun delete(journal: Journal)

    @Query("SELECT * FROM journals_table ORDER BY id DESC")
    fun getAllJournals() : LiveData<List<Journal>>

    @Query("UPDATE journals_table Set title = :title, journal = :journal WHERE id = :id")
    suspend fun update(id : Int? , title : String? , journal: String?)
}