package com.rafao1991.mobilechallenge.moneyexchange.domain

import java.lang.Exception

const val ERROR = "Something went wrong during the currency exchange operation."
const val USD = "USD"

class Exchange(
    private val amount: Double,
    private val originCurrency: String,
    private val newCurrency: String,
    private val quotes: Map<String, Double>) {

    fun getExchanged(): Double {
        if (originCurrency == USD || newCurrency == USD) {
            return exchangeWithUSD()
        }

        return exchange()
    }

    private fun exchange(): Double {
        TODO("Not yet implemented")
    }

    private fun exchangeWithUSD(): Double {
        if (originCurrency == USD) {
            return exchangeFromUSD()
        }

        return exchangeToUSD()
    }

    private fun exchangeToUSD(): Double {
        val key = newCurrency + originCurrency

        if (quotes.containsKey(key)) {
            val quote = quotes[key]
            try {
                return amount / quote!!
            } catch (e: Exception) {
                throw Exception(ERROR, e)
            }
        }

        throw Exception(ERROR)
    }

    private fun exchangeFromUSD(): Double {
        val key = originCurrency + newCurrency

        if (quotes.containsKey(key)) {
            val quote = quotes[key]
            try {
                return amount * quote!!
            } catch (e: Exception) {
                throw Exception(ERROR, e)
            }
        }

        throw Exception(ERROR)
    }
}