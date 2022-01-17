package com.bettingstrategies.domain.use_cases

data class BetUseCases(
    val changeFavouriteStatus: ChangeFavouriteStatus,
    val getMoreInfo: GetMoreInfo,
    val getAllStrategies: GetAllStrategies,
    val getAllFavouriteStrategies: GetAllFavouriteStrategies
)
