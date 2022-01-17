package com.bettingstrategies.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bettingstrategies.data.model.BettingStrategyEntity

@Dao
interface BetDao {

    @Query("SELECT * FROM bet_table")
    suspend fun getAllStrategies(): List<BettingStrategyEntity>

    @Query("SELECT * FROM bet_table WHERE isFavourite = 1")
    suspend fun getAllFavouriteStrategies(): List<BettingStrategyEntity>

    @Query("SELECT * FROM bet_table WHERE id = :id")
    suspend fun getStrategy(id: Int): BettingStrategyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun changeFavouriteStatus(strategy: BettingStrategyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStrategy(strategy: BettingStrategyEntity): Long

    @Query("DELETE FROM bet_table")
    suspend fun deleteAll()


}