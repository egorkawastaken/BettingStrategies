package com.bettingstrategies.domain.use_cases

import com.bettingstrategies.domain.repository.BetRepository

class GetAllStrategies(val repo: BetRepository) {

    suspend operator fun invoke() {
        repo.getStrategies()
    }

}