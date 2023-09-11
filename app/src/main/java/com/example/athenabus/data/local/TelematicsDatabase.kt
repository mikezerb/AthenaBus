package com.example.athenabus.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.athenabus.data.remote.dto.LineDtoItem

@Database(
    entities = [LineDtoItem::class],
    version = 1
)
abstract class TelematicsDatabase : RoomDatabase() {
    abstract val dao: TelematicsDao
}