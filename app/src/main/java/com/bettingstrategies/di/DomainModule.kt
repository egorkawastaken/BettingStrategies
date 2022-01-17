package com.bettingstrategies.di

import com.bettingstrategies.domain.use_cases.ChangeFavouriteStatus
import com.bettingstrategies.domain.use_cases.GetAllFavouriteStrategies
import com.bettingstrategies.domain.use_cases.GetAllStrategies
import com.bettingstrategies.domain.use_cases.GetMoreInfo
import org.koin.dsl.module

val domainModule = module {

    factory {
        ChangeFavouriteStatus(repo = get())
    }

    factory {
        GetMoreInfo(repo = get())
    }

    factory {
        GetAllStrategies(repo = get())
    }

    factory {
        GetAllFavouriteStrategies(repo = get())
    }

}