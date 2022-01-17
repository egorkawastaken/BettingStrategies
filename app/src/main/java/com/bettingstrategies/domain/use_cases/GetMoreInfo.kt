package com.bettingstrategies.domain.use_cases

import com.bettingstrategies.domain.repository.BetRepository

class GetMoreInfo(val repo: BetRepository) {

    suspend operator fun invoke(id: Int) {
        repo.getStrategyById(id)
    }
}