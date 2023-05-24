package com.example.mindmate.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.mindmate.Models.Journal
import com.project.mindmate.utilities.DATABASE_NAME


@Database(entities = arrayOf(Journal::class), version = 1, exportSchema = false)
abstract class JournalDatabase : RoomDatabase() {

    abstract fun getJournalDao() : JournalDao

    companion object{
        @Volatile
        private var INSTANCE : JournalDatabase? = null

        fun getDatabase(context: Context) : JournalDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JournalDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}