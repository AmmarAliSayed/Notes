package com.ammar.notes.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//step 1
@Entity(tableName = "note_table")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    var id:Int,
    @ColumnInfo(name = "note_title")
    var title:String ,
    @ColumnInfo(name = "note_description")
    var description:String,
   @ColumnInfo(name = "note_time")
     var timestamp:String
) {


}