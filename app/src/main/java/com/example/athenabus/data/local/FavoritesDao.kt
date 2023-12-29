package com.example.athenabus.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.athenabus.data.local.entity.FavoriteLinesEntity
import com.example.athenabus.data.local.entity.FavoriteStopsEntity


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


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteStops(busStop: List<FavoriteStopsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteStop(busStop: FavoriteStopsEntity)

    @Query(
        "DELETE FROM " +
                "FavoriteStopsEntity"
    )
    suspend fun clearFavStops()

    @Query("SELECT * FROM FavoriteStopsEntity")
    suspend fun getFavoriteStops(): List<FavoriteStopsEntity>

    @Query(
        """
            SELECT * FROM FavoriteStopsEntity
            WHERE StopCode = :query
        """
    )
    suspend fun getFavStop(query: String): FavoriteStopsEntity


    @Query(
        """
            SELECT COUNT(*) FROM FavoriteStopsEntity
            WHERE StopCode = :query
        """
    )
    suspend fun checkFavoriteStop(query: String): Int


    @Query(
        """
           DELETE FROM FavoriteStopsEntity 
           WHERE StopCode = :query
        """
    )
    suspend fun deleteFavoriteStop(query: String)

}