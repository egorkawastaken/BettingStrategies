package com.bettingstrategies.presentation.fragments.strategies.events

import com.bettingstrategies.data.model.BettingStrategyEntity

sealed class StrategiesEvent {
    data class ChangeFavouriteStatus(val strategy: BettingStrategyEntity): StrategyEvent()
}