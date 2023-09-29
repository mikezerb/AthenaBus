package com.example.athenabus.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.athenabus.data.local.entity.RouteEntity

@Dao
interface TelematicsRouteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutes(busRoutes: List<RouteEntity>)

    @Query(
        "DELETE FROM " +
                "RouteEntity"
    )
    suspend fun clearRoutes()

    @Query("SELECT * FROM RouteEntity")
    suspend fun getAllRoutes(): List<RouteEntity>

    @Query(
        """
            SELECT * 
            FROM RouteEntity
            WHERE LineCode LIKE '%' || UPPER(:query) || '%' AND RouteType = "1"
        """
    )
    suspend fun getRoutesFromLineCode(query: String): List<RouteEntity>

}