package com.rafao1991.mobilechallenge.moneyexchange

import com.rafao1991.mobilechallenge.moneyexchange.domain.Exchange
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExchangeUnitTest {
    lateinit var exchange: Exchange

    private val usd = "USD"
    private val brl = "BRL"
    private val gbp = "GBP"
    private val kgs = "KGS"

    private val amount = 100.00
    private val delta = 0.0000000001

    private val quotes = HashMap<String, Double>()

    @Before
    fun setup() {
        quotes[usd + brl] = 5.342801
        quotes[usd + gbp] = 0.716315
        quotes[usd + kgs] = 84.795339
    }

    @Test
    fun exchange_USDBRL() {
        exchange = Exchange(amount, usd, brl, quotes)
        assertEquals(exchange.getExchanged(), 534.2801, delta)
    }

    @Test
    fun exchange_BRLUSD() {
        exchange = Exchange(amount, brl, usd, quotes)
        assertEquals(exchange.getExchanged(), 18.71677421637078, delta)
    }

    @Test
    fun exchange_USDGBP() {
        exchange = Exchange(amount, usd, gbp, quotes)
        assertEquals(exchange.getExchanged(), 71.6315, delta)
    }

    @Test
    fun exchange_GBPUSD() {
        exchange = Exchange(amount, gbp, usd, quotes)
        assertEquals(exchange.getExchanged(), 139.6033867781632, delta)
    }

    @Test
    fun exchange_USDKGS() {
        exchange = Exchange(amount, usd, kgs, quotes)
        assertEquals(exchange.getExchanged(), 8479.5339, delta)
    }

    @Test
    fun exchange_KGSUSD() {
        exchange = Exchange(amount, kgs, usd, quotes)
        assertEquals(exchange.getExchanged(), 1.179310103353676, delta)
    }
}