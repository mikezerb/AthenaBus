package com.example.athenabus.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.athenabus.data.local.entity.BusLineEntity

@Dao
interface TelematicsLineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusLines(busLine: List<BusLineEntity>)

    @Query(
        "DELETE FROM " +
                "BusLineEntity"
    )
    suspend fun clearBusLines()

    @Query("SELECT * FROM BusLineEntity")
    suspend fun getBusLines(): List<BusLineEntity>

    @Query(
        """
            SELECT * 
            FROM BusLineEntity
            WHERE LineID LIKE '%' || UPPER(:query) || '%'
        """
    )
    suspend fun searchBusLines(query: String): BusLineEntity

    @Query(
        """
            SELECT * 
            FROM BusLineEntity
            WHERE LineID = UPPER(:query)
        """
    )
    suspend fun getLinesFromLineID(query: String): List<BusLineEntity>


    @Query(
        """
            SELECT LineCode 
            FROM BusLineEntity
            WHERE LineID = UPPER(:query) 
        """
    )
    suspend fun getLineCodesFromLineID(query: String): List<String>


    @Query(
        """
            SELECT *
            FROM BusLineEntity
            WHERE isFavorite = 1 
        """
    )
    suspend fun getFavoriteLines(): List<BusLineEntity>


    @Query(
        """
            UPDATE BusLineEntity 
        SET isFavorite = NOT isFavorite where lineID = :query
        """
    )
    suspend fun toggleFavoriteLine(query: String)


}
