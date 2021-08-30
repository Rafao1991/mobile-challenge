package com.rafao1991.mobilechallenge.moneyexchange

import android.app.Application
import com.rafao1991.mobilechallenge.moneyexchange.data.local.ExchangeRoomDatabase
import com.rafao1991.mobilechallenge.moneyexchange.data.reposiroty.CurrencyRepository
import com.rafao1991.mobilechallenge.moneyexchange.data.reposiroty.QuoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ExchangeApplication: Application() {
    private val database by lazy { ExchangeRoomDatabase.getInstance(this) }
    val currencyRepository by lazy { CurrencyRepository(database.currencyDAO()) }
    val quoteRepository by lazy { QuoteRepository(database.quoteDAO()) }
}