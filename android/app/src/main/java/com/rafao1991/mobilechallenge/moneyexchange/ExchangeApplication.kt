package com.rafao1991.mobilechallenge.moneyexchange

import android.app.Application
import androidx.work.*
import com.rafao1991.mobilechallenge.moneyexchange.data.local.ExchangeRoomDatabase
import com.rafao1991.mobilechallenge.moneyexchange.data.reposiroty.CurrencyRepository
import com.rafao1991.mobilechallenge.moneyexchange.data.reposiroty.QuoteRepository
import com.rafao1991.mobilechallenge.moneyexchange.worker.TAG
import com.rafao1991.mobilechallenge.moneyexchange.worker.UpdateCurrenciesWorker
import java.util.concurrent.TimeUnit

class ExchangeApplication: Application() {
    private val database by lazy { ExchangeRoomDatabase.getInstance(this) }
    val currencyRepository by lazy { CurrencyRepository(database.currencyDAO()) }
    val quoteRepository by lazy { QuoteRepository(database.quoteDAO()) }

    override fun onCreate() {
        super.onCreate()

        val repeatInterval: Long = 60
        val flexTimeInterval: Long = 10
        val backoffDelay: Long = 30

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<UpdateCurrenciesWorker>(
            repeatInterval, TimeUnit.MINUTES, flexTimeInterval, TimeUnit.MINUTES
        ).setBackoffCriteria(
            BackoffPolicy.EXPONENTIAL, backoffDelay, TimeUnit.SECONDS
        ).setConstraints(
            constraints
        ).addTag(
            TAG
        ).build()

        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork(
                TAG,
                ExistingPeriodicWorkPolicy.REPLACE,
                periodicWorkRequest
            )
    }
}