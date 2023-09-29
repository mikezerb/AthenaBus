package com.example.athenabus.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.athenabus.data.local.entity.BusLineEntity
import com.example.athenabus.data.local.entity.RouteEntity

@Database(
    entities = [BusLineEntity::class, RouteEntity::class],
    version = 1
)
abstract class TelematicsDatabase : RoomDatabase() {
    abstract val dao: TelematicsDao
    abstract val linesDao: TelematicsLineDao
    abstract val routeDao: TelematicsRouteDao
}