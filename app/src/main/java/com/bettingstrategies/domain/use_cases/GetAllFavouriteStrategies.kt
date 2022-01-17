package com.bettingstrategies.domain.use_cases

import com.bettingstrategies.domain.model.BettingStrategy
import com.bettingstrategies.domain.repository.BetRepository

class GetAllFavouriteStrategies(val repo: BetRepository) {

    suspend operator fun invoke(strategy: BettingStrategy) {
        repo.getFavouriteStrategies()
    }

}