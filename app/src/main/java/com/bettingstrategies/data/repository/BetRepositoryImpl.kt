package com.bettingstrategies.data.repository

import com.bettingstrategies.data.model.BettingStrategyEntity
import com.bettingstrategies.data.repository.local.LocalDataSourceRepository
import com.bettingstrategies.domain.model.BettingStrategy
import com.bettingstrategies.domain.repository.BetRepository

class BetRepositoryImpl(
    val localDataSource: LocalDataSourceRepository
): BetRepository {

    override suspend fun changeFavouriteStatus(strategy: BettingStrategy) {
        localDataSource.updateFavouriteStatus(strategy = strategy)
    }

    override suspend fun getStrategyById(id: Int): BettingStrategy =
        localDataSource.getStrategyById(id = id)


    override suspend fun getStrategies() =
        localDataSource.getAllStrategies()


    override suspend fun getFavouriteStrategies(): List<BettingStrategyEntity> =
        localDataSource.getAllFavouriteStrategies()

    override suspend fun insertStrategy() {
        localDataSource.insertStrategy()
    }

    override suspend fun deleteAll() {
        localDataSource.deleteAll()
    }
}