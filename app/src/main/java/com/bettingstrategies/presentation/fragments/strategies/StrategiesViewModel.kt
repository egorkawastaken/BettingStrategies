package com.bettingstrategies.presentation.fragments.strategies

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

class StrategiesViewModel(val repo: BetRepository) : ViewModel() {

    private val _strategiesList = MutableLiveData<List<BettingStrategyEntity>>()
    val strategiesList: LiveData<List<BettingStrategyEntity>> = _strategiesList

    fun downloadStrategies() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertStrategy()
        }
    }

    fun getStrategies() {
        viewModelScope.launch(Dispatchers.IO) {
//            delay(1000)
            val list = repo.getStrategies()
            _strategiesList.postValue(list)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAll()
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