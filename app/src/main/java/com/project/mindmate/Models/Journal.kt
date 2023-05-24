package com.project.mindmate.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journals_table")
data class Journal(
    @PrimaryKey(autoGenerate = true)
    val id : Int? ,

    @ColumnInfo(name = "title")
    val title : String? ,

    @ColumnInfo(name = "journal")
    val journal : String? ,

    @ColumnInfo(name = "date")
    val date : String?

) : java.io.Serializable
