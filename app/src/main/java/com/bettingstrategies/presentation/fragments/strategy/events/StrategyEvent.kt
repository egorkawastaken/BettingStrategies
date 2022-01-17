package com.bettingstrategies.presentation.fragments.strategies.events

import com.bettingstrategies.data.model.BettingStrategyEntity

sealed class StrategyEvent {
    data class ChangeFavouriteStatus(val strategy: BettingStrategyEntity): StrategyEvent()
}