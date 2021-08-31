package com.rafao1991.mobilechallenge.moneyexchange.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rafao1991.mobilechallenge.moneyexchange.ExchangeApplication

const val TAG = "UpdateCurrenciesWorker"
class UpdateCurrenciesWorker(
    context: Context,
    workerParameters: WorkerParameters
): Worker(context, workerParameters) {

    override fun doWork(): Result {
        val context = applicationContext as ExchangeApplication

        return try {
            context.currencyRepository.getCurrenciesFromRemote()
            context.quoteRepository.getQuotesFromRemote()
            Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error applying blur")
            Result.failure()
        }
    }
}