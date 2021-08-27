package com.rafao1991.mobilechallenge.moneyexchange.data

data class CurrencyLiveQuotes(
    val success: Boolean,
    val source: String,
    val quotes: Map<String, Double>
    )