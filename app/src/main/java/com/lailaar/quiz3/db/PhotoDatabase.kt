package com.lailaar.quiz3.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yourpackage.data.model.Photo

@Database(entities = [Photo::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}
