package com.bettingstrategies.di

import com.bettingstrategies.presentation.fragments.favourites.FavouritesViewModel
import com.bettingstrategies.presentation.fragments.strategies.StrategiesViewModel
import com.bettingstrategies.presentation.fragments.strategy.StrategyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<StrategiesViewModel>{
        StrategiesViewModel(repo = get())
    }
    viewModel<FavouritesViewModel>{
        FavouritesViewModel(repo = get())
    }
    viewModel<StrategyViewModel>{
        StrategyViewModel(repo = get())
    }


}