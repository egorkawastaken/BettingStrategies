package com.bettingstrategies.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bettingstrategies.data.model.BettingStrategyEntity

@Database(entities = [BettingStrategyEntity::class], version = 1, exportSchema = false)
abstract class BetDataBase: RoomDatabase() {

    abstract fun getBetDao(): BetDao

    companion object {

        fun getBetDataBase(context: Context): BetDataBase {
            return Room.databaseBuilder(
                context,
                BetDataBase::class.java,
                "strategies_database"
            ).build()
        }

    }
}