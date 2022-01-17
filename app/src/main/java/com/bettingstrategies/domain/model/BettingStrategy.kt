package com.bettingstrategies.domain.model


data class BettingStrategy(
    val id: Int = 0,
    val title: String,
    val description: String,
    val urlToImage: String
)
