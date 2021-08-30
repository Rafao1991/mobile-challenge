package com.rafao1991.mobilechallenge.moneyexchange.data.local

import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rafao1991.mobilechallenge.moneyexchange.data.local.dao.CurrencyDAO
import com.rafao1991.mobilechallenge.moneyexchange.data.local.dao.QuoteDAO
import com.rafao1991.mobilechallenge.moneyexchange.data.local.entity.CurrencyEntity
import com.rafao1991.mobilechallenge.moneyexchange.data.local.entity.QuoteEntity
import com.rafao1991.mobilechallenge.moneyexchange.data.remote.CurrencyApi
import com.rafao1991.mobilechallenge.moneyexchange.util.EXCHANGE_DATABASE
import com.rafao1991.mobilechallenge.moneyexchange.util.EXCHANGE_DATABASE_VERSION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [CurrencyEntity::class, QuoteEntity::class],
    version = EXCHANGE_DATABASE_VERSION,
    exportSchema = false)
abstract class ExchangeRoomDatabase: RoomDatabase() {

    abstract fun currencyDAO(): CurrencyDAO
    abstract fun quoteDAO(): QuoteDAO

    companion object {
        @Volatile
        private var INSTANCE: ExchangeRoomDatabase? = null

        fun getInstance(context: Context): ExchangeRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    ExchangeRoomDatabase::class.java,
                    EXCHANGE_DATABASE)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}