package com.example.athenabus.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.athenabus.data.remote.dto.LineDtoItem

@Dao
interface TelematicsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusLines(busLineDtoItem: LineDtoItem)

    @Query("DELETE FROM " +
            "bus_line")
    suspend fun clearBusLines()

    @Query(
        """
            SELECT * 
            FROM bus_line
            WHERE LineID LIKE '%' || :query || '%'
        """
    )
    suspend fun searchBusLine(query: String): LineDtoItem

}