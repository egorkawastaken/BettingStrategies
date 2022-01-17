package com.bettingstrategies.domain.repository

import com.bettingstrategies.data.model.BettingStrategyEntity
import com.bettingstrategies.domain.model.BettingStrategy

interface BetRepository {

    suspend fun changeFavouriteStatus(strategy: BettingStrategy)

    suspend fun getStrategyById(id: Int): BettingStrategy

    suspend fun getStrategies(): List<BettingStrategyEntity>

    suspend fun getFavouriteStrategies(): List<BettingStrategyEntity>

    suspend fun insertStrategy()

    suspend fun deleteAll()

}