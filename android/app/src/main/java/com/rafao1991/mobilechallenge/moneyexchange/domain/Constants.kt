package com.rafao1991.mobilechallenge.moneyexchange.domain

const val ERROR = "Something went wrong during the currency exchange operation."
const val USD = "USD"
const val USD_TEXT = "United States Dollar"
const val BRL = "BRL"
const val BRL_TEXT = "Brazilian Real"

enum class Currency {
    ORIGIN,
    TARGET
}