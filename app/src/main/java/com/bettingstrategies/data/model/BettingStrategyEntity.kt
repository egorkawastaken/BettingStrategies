package com.bettingstrategies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "bet_table")
data class BettingStrategyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val isFavourite: Boolean = false,
    val urlToImage: String
): Serializable