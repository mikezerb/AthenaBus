package com.example.athenabus.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.athenabus.data.local.entity.FavoriteLinesEntity


@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteLines(busLine: List<FavoriteLinesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteLine(busLine: FavoriteLinesEntity)

    @Query(
        "DELETE FROM " +
                "FavoriteLinesEntity"
    )
    suspend fun clearBusLines()

    @Query("SELECT * FROM FavoriteLinesEntity")
    suspend fun getFavoriteLines(): List<FavoriteLinesEntity>

    @Query(
        """
            SELECT * FROM FavoriteLinesEntity
            WHERE LineID = :query
        """
    )
    suspend fun getFavLine(query: String): FavoriteLinesEntity


    @Query(
        """
            SELECT COUNT(*) FROM FavoriteLinesEntity
            WHERE LineID = :query
        """
    )
    suspend fun checkFavoriteLine(query: String): Int


    @Query(
        """
           DELETE FROM FavoriteLinesEntity 
           WHERE LineID = :query
        """
    )
    suspend fun deleteFavoriteFromID(query: String)

}