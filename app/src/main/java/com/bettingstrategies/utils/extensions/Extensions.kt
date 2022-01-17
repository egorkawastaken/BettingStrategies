package com.bettingstrategies.utils.extensions

import com.bettingstrategies.data.model.BettingStrategyEntity

fun com.bettingstrategies.domain.model.BettingStrategy.transformToEntity(): BettingStrategyEntity {
    return BettingStrategyEntity(
        id = 0,
        title = this.title,
        description = this.description,
        isFavourite = false,
        urlToImage = this.urlToImage
    )
}

fun com.bettingstrategies.data.model.BettingStrategyEntity.transformToModel(): com.bettingstrategies.domain.model.BettingStrategy {
    return com.bettingstrategies.domain.model.BettingStrategy(
        id =this.id,
        title = this.title,
        description = this.description,
        urlToImage = this.urlToImage
    )
}