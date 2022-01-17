package com.bettingstrategies.presentation.fragments.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettingstrategies.data.model.BettingStrategyEntity
import com.bettingstrategies.domain.repository.BetRepository
import com.bettingstrategies.presentation.fragments.strategies.events.StrategyEvent
import com.bettingstrategies.utils.extensions.transformToModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesViewModel(val repo: BetRepository): ViewModel() {

    private val _favouriteList = MutableLiveData<List<BettingStrategyEntity>>()
    val favouriteList: LiveData<List<BettingStrategyEntity>> = _favouriteList

    fun isListEmpty() = favouriteList.value?.isEmpty() ?: true

    fun getStrategies() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = repo.getFavouriteStrategies()
            _favouriteList.postValue(list)
        }
    }

    fun onEvent(event: StrategyEvent) {
        when (event) {
            is StrategyEvent.ChangeFavouriteStatus -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repo.changeFavouriteStatus(event.strategy.transformToModel())
                    getStrategies()
                }
            }
        }
    }

}