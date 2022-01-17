package com.bettingstrategies.data.repository.local

import com.bettingstrategies.data.model.BettingStrategyEntity
import com.bettingstrategies.domain.model.BettingStrategy

interface LocalDataSourceRepository {

    suspend fun getAllStrategies(): List<BettingStrategyEntity>

    suspend fun getAllFavouriteStrategies(): List<BettingStrategyEntity>

    suspend fun getStrategyById(id: Int): BettingStrategy

    suspend fun updateFavouriteStatus(strategy: BettingStrategy)

    suspend fun insertStrategy()

    suspend fun deleteAll()

}