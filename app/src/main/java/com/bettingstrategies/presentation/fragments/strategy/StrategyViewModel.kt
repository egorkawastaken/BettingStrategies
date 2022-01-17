package com.bettingstrategies.presentation.fragments.strategy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingstrategies.domain.repository.BetRepository
import com.bettingstrategies.presentation.fragments.strategies.events.StrategyEvent
import com.bettingstrategies.utils.extensions.transformToModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StrategyViewModel(val repo: BetRepository) : ViewModel() {

    fun onEvent(event: StrategyEvent) {
        when (event) {
            is StrategyEvent.ChangeFavouriteStatus -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repo.changeFavouriteStatus(event.strategy.transformToModel())
                }
            }

        }
    }
}