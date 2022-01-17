package com.bettingstrategies.di

import com.bettingstrategies.data.db.BetDataBase
import com.bettingstrategies.data.repository.BetRepositoryImpl
import com.bettingstrategies.data.repository.LocalDataSourceImpl
import com.bettingstrategies.data.repository.local.LocalDataSourceRepository
import com.bettingstrategies.domain.repository.BetRepository
import org.koin.dsl.module

val dataModule = module {

    single {
        BetDataBase.getBetDataBase(context = get())
    }

    single<LocalDataSourceRepository> {
        LocalDataSourceImpl(dataBase = get(), context = get())
    }

    single<BetRepository> {
        BetRepositoryImpl(localDataSource = get())
    }

}