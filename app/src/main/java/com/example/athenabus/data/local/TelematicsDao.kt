package com.example.athenabus.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.athenabus.data.local.entity.BusLineEntity

@Dao
interface TelematicsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusLines(busLine: List<BusLineEntity>)

    @Query(
        "DELETE FROM " +
                "BusLineEntity"
    )
    suspend fun clearBusLines()

    @Query(
        """
            SELECT * 
            FROM BusLineEntity
            WHERE LineID LIKE '%' || :query || '%'
        """
    )
    suspend fun searchBusLine(query: String): BusLineEntity

    @Query("SELECT * FROM BusLineEntity")
    suspend fun getBusLines(): List<BusLineEntity>

}